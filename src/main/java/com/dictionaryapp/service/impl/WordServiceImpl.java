package com.dictionaryapp.service.impl;

import com.dictionaryapp.controller.dto.WordDTO;
import com.dictionaryapp.controller.dto.WordDisplayDTO;
import com.dictionaryapp.model.entity.Language;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.model.entity.Word;
import com.dictionaryapp.repo.LanguageRepository;
import com.dictionaryapp.repo.UserRepository;
import com.dictionaryapp.repo.WordRepository;
import com.dictionaryapp.service.WordService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final ModelMapper modelMapper;

    public WordServiceImpl(
            WordRepository wordRepository,
            UserRepository userRepository,
            LanguageRepository languageRepository,
            ModelMapper modelMapper) {
        this.wordRepository = wordRepository;
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void addWord(WordDTO wordDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Language language = languageRepository.findByLanguageName(wordDTO.getLanguage())
                .orElseThrow(() -> new IllegalArgumentException("Language not found: " + wordDTO.getLanguage()));

        Word word = modelMapper.map(wordDTO, Word.class);
        word.setAddedBy(user);
        word.setLanguage(language);

        wordRepository.save(word);
    }

    @Override
    @Transactional
    public void removeWordById(Long id) {
        if (!wordRepository.existsById(id)) {
            throw new IllegalArgumentException("Word with ID " + id + " does not exist.");
        }
        wordRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void removeAllWords() {
        wordRepository.deleteAll();
    }

    @Override
    public Map<String, List<WordDisplayDTO>> getWordsGroupedByLanguage() {
        return wordRepository.findAll().stream()
                .map(word -> {
                    WordDisplayDTO dto = modelMapper.map(word, WordDisplayDTO.class);
                    dto.setUsername(word.getAddedBy() != null ? word.getAddedBy().getUsername() : "Unknown");
                    return dto;
                })
                .collect(Collectors.groupingBy(word -> word.getLanguage().name()));
    }
}
