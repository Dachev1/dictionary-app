package com.dictionaryapp.service;

import com.dictionaryapp.controller.dto.WordDTO;
import com.dictionaryapp.controller.dto.WordDisplayDTO;

import java.util.List;
import java.util.Map;

public interface WordService {
    void addWord(WordDTO wordDTO, String username);

    void removeWordById(Long id);

    void removeAllWords();

    Map<String, List<WordDisplayDTO>> getWordsGroupedByLanguage();
}
