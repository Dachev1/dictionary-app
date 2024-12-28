package com.dictionaryapp.controller;

import com.dictionaryapp.controller.dto.WordDTO;
import com.dictionaryapp.service.WordService;
import com.dictionaryapp.session.SessionUser;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/words")
public class WordController {

    private static final Logger logger = LoggerFactory.getLogger(WordController.class);

    private final WordService wordService;
    private final SessionUser sessionUser;

    public WordController(WordService wordService, SessionUser sessionUser) {
        this.wordService = wordService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/add")
    public String addWordPage(Model model, RedirectAttributes redirectAttributes) {
        if (!sessionUser.isLoggedIn()) {
            redirectAttributes.addFlashAttribute("errorMessage", "You must be logged in to access this page.");
            return "redirect:/users/login";
        }
        if (!model.containsAttribute("word")) {
            model.addAttribute("word", new WordDTO());
        }
        return "word-add";
    }

    @PostMapping("/add")
    public String addWord(@Valid @ModelAttribute("word") WordDTO wordDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.word", bindingResult);
            redirectAttributes.addFlashAttribute("word", wordDTO);
            return "redirect:/words/add";
        }

        wordService.addWord(wordDTO, sessionUser.getUsername());

        redirectAttributes.addFlashAttribute("successMessage", "Word added successfully!");
        return "redirect:/home";
    }

    @DeleteMapping("/remove/{id}")
    public String removeWord(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            wordService.removeWordById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Word removed successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/home";
    }

    @DeleteMapping("/remove-all")
    public String removeAllWords(RedirectAttributes redirectAttributes) {
        wordService.removeAllWords();
        redirectAttributes.addFlashAttribute("successMessage", "All words removed successfully!");
        return "redirect:/home";
    }
}
