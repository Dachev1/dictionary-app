package com.dictionaryapp.controller;

import com.dictionaryapp.controller.dto.WordDisplayDTO;
import com.dictionaryapp.service.WordService;
import com.dictionaryapp.session.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final SessionUser sessionUser;
    private final WordService wordService;

    public HomeController(SessionUser sessionUser, WordService wordService) {
        this.sessionUser = sessionUser;
        this.wordService = wordService;
    }

    @GetMapping("/")
    public String showIndexPage(Model model) {
        if (sessionUser.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        if (!sessionUser.isLoggedIn()) {
            return "redirect:/";
        }

        model.addAttribute("username", sessionUser.getUsername());

        Map<String, List<WordDisplayDTO>> wordsByLanguage = wordService.getWordsGroupedByLanguage();
        if (wordsByLanguage == null) {
            wordsByLanguage = Collections.emptyMap();
        }

        model.addAttribute("wordsByLanguage", wordsByLanguage);

        int totalWordCount = wordsByLanguage.values()
                .stream()
                .mapToInt(List::size)
                .sum();

        model.addAttribute("totalWordCount", totalWordCount);

        return "home";
    }
}
