package com.dictionaryapp.controller.user;

import com.dictionaryapp.controller.dto.user.UserRegisterDTO;
import com.dictionaryapp.service.UserService;
import com.dictionaryapp.session.SessionUser;
import jakarta.validation.Valid;
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
public class RegisterController {

    private final UserService userService;
    private final SessionUser sessionUser;

    public RegisterController(UserService userService, SessionUser sessionUser) {
        this.userService = userService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (sessionUser.isLoggedIn()) {
            return "redirect:/home";
        }
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserRegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (userService.isUsernameTaken(userRegisterDTO.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username is already taken");
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.user", "Passwords do not match");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            return "redirect:/users/register";
        }

        userService.register(userRegisterDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/users/login";
    }
}
