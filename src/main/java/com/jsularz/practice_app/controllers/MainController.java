package com.jsularz.practice_app.controllers;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.dto.UserLoginFormDto;
import com.jsularz.practice_app.exceptions.UserNotExistsException;
import com.jsularz.practice_app.models.User;
import com.jsularz.practice_app.models.VerificationToken;
import com.jsularz.practice_app.registrationUtill.RegistrationCompleteEvent;
import com.jsularz.practice_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
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
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/login")
    private String showLoginForm(final Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserLoginFormDto());
        }
        return "login";
    }

    @GetMapping("/access-denied")
    private String accessDenied() {
        return "access-denied";
    }


    @PostMapping("/logout")
    private String logoutPage(final HttpServletRequest request, final HttpServletResponse response) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?success";
    }

    @GetMapping("/register")
    private String getRegistration(final Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserCreateFormDto());
        }
        return "registration";
    }

    @PostMapping(value = "/register")
    private String postRegistration( @ModelAttribute @Valid final  UserCreateFormDto user, final BindingResult result,
                                    final RedirectAttributes redirectAttributes, final WebRequest webRequest) {
        try {
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
                redirectAttributes.addFlashAttribute("user", user);
                throw new BindException(result);
            }
            if (userService.checkEmailExist(user.getEmail())) {
                throw new UserNotExistsException("User with email: "+ user.getEmail()+" already exists.");
            }
            final User registered = userService.createNewUserAccount(user);
            final String appUrl = webRequest.getContextPath();
            eventPublisher.publishEvent(
                    new RegistrationCompleteEvent(registered, webRequest.getLocale(), appUrl));
            return "redirect:/login?success";

        } catch (UserNotExistsException error) {
            return "redirect:/register?exists";
        } catch (BindException error) {
            return "redirect:/register";
        }
    }

    @GetMapping("/badUser")
    private String getBadUserSite() {
        return "badUser";
    }

    @GetMapping("/user/home")
    private String getUserHome() {
        return "user/home";
    }


    @GetMapping("/registrationConfirm")
    private String confirmRegistration(final Model model, @RequestParam("token")final String token) {

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
        userService.saveUser(user);
        return "redirect:/login?confirmed";
    }

    @GetMapping("/user/offer")
    public String offerView(){
        return "user/offer";
    }
}
