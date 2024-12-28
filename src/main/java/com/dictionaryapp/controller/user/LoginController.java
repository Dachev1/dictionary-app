package com.dictionaryapp.controller.user;

import com.dictionaryapp.controller.dto.user.UserLoginDTO;
import com.dictionaryapp.service.UserService;
import com.dictionaryapp.session.SessionUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class LoginController {

    private final UserService userService;
    private final SessionUser sessionUser;

    @Autowired
    public LoginController(UserService userService, SessionUser sessionUser) {
        this.userService = userService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        if (sessionUser.isLoggedIn()) {
            return "redirect:/home";
        }
        if (!model.containsAttribute("login")) {
            model.addAttribute("login", new UserLoginDTO());
        }
        return "login";
    }

    @PostMapping("/login")
    public String authenticateUser(
            @Valid @ModelAttribute("login") UserLoginDTO loginDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("login", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.login", bindingResult);
            return "redirect:/users/login";
        }

        if (!userService.authenticate(loginDTO.getUsername(), loginDTO.getPassword())) {
            redirectAttributes.addFlashAttribute("login", loginDTO);
            redirectAttributes.addFlashAttribute("errorMessage", "Incorrect username or password!");
            return "redirect:/users/login";
        }

        sessionUser.setId(userService.getUserIdByUsername(loginDTO.getUsername()));
        sessionUser.setUsername(loginDTO.getUsername());

        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logout() {
        sessionUser.logout();
        return "redirect:/";
    }
}
