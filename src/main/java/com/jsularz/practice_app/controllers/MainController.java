package com.jsularz.practice_app.controllers;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.dto.UserLoginFormDto;
import com.jsularz.practice_app.exceptions.UserNotExistsException;
import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.models.VerificationToken;
import com.jsularz.practice_app.registrationUtill.RegistrationCompleteEvent;
import com.jsularz.practice_app.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/login")
    private String showLoginForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserLoginFormDto());
        }
        return "login";
    }

    @GetMapping("/access-denied")
    private String accessDenied() {
        return "access-denied";
    }

    @PostMapping("/login")
    private String sendLoginFormData(@ModelAttribute @Valid UserLoginFormDto user, BindingResult result,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/login";
        }
        return "redirect:/admin/admin";
    }


    @PostMapping("/logout")
    private String logoutPage(final HttpServletRequest request, final HttpServletResponse response) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    @GetMapping("/register")
    private String getRegistration(final Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserCreateFormDto());
        }
        return "registration";
    }

    @PostMapping("/register")
    private String postRegistration(@ModelAttribute @Valid final  UserCreateFormDto user, final BindingResult result,
                                    final RedirectAttributes redirectAttributes, final WebRequest webRequest) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/register";
        }
        if (userService.emailExist(user.getEmail())) {
            return "redirect:/register?exist";
        }
        final User registered = userService.createNewUserAccount(user);

        try {
            String appUrl = webRequest.getContextPath();
            eventPublisher.publishEvent(new RegistrationCompleteEvent(registered, webRequest.getLocale(), appUrl));
            return "redirect:/login?success";
        } catch (UserNotExistsException e) {
            return "redirect:/register";
        }
    }

    @GetMapping("/badUser")
    private String getBadUserSite() {
        return "badUser";
    }

    @GetMapping("/registrationConfirm")
    private String confirmRegistration(final Model model, @RequestParam("token") final String token) {

        final VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", "Token null");
            return "redirect:/badUser";
        }
        final User user = verificationToken.getUser();
        final LocalDateTime todayTime = LocalDateTime.now();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.from(todayTime))) {
            model.addAttribute("message", "Token expired");
            return "redirect:/badUser";
        }
        user.setStatus(true);
        userService.saveRegisteredUser(user);
        return "redirect:/login?confirmed";
    }
}
