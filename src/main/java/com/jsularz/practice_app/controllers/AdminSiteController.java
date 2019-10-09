package com.jsularz.practice_app.controllers;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.dto.UserUpdateFormDto;
import com.jsularz.practice_app.exceptions.EmailExistsException;
import com.jsularz.practice_app.exceptions.UserNotExistsException;
import com.jsularz.practice_app.models.Role;
import com.jsularz.practice_app.services.impl.RoleServiceImpl;
import com.jsularz.practice_app.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class AdminSiteController {


    @Autowired
    private UserServiceImpl userService; //czy tu powininem użyć interfejsu zamiast klasy?
    @Autowired
    private RoleServiceImpl roleService;


    @GetMapping("/add")
    private String showRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserCreateFormDto());
        }
        model.addAttribute("roles", roleService.findAll());
        return "admin/add";
    }

    @PostMapping("/add")
    private String registerUserAccount(@ModelAttribute("user") @Valid UserCreateFormDto accountDto,@ModelAttribute("roles") HashSet<Role> roles,
                                       BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", accountDto);
            redirectAttributes.addFlashAttribute("roles",roles);
            return "redirect:/admin/add";
        }
        try {
            userService.createNewUserAccount(accountDto);
            return "redirect:/admin/admin";
        } catch (EmailExistsException e) {
            return "/admin/error";
        }
    }

    @GetMapping("/admin")
    private String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/admin";
    }

    @GetMapping("/delete/{id}")
    private String deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteById(id);
        } catch (UserNotExistsException e) {
            return "redirect:/admin/error";
        }
        return "redirect:/admin/admin";
    }

    @PostMapping("/edit/{id}")
    private String updateUser(@Valid @ModelAttribute("user") UserUpdateFormDto user, BindingResult result, RedirectAttributes redirectAttributes) {
        System.out.println(result.getFieldErrors());
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/admin/edit/" + user.getId();
        }
        this.userService.updateUser(user.getId(), user);
        return "redirect:/admin/admin";
    }

    @GetMapping("/edit/{id}")
    private String transferUserToEdit(@PathVariable("id") Long id, Model model) {
        UserCreateFormDto user = new UserCreateFormDto(userService.findById(id));
        Iterable<Role> roles = roleService.findAll();
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
        }
        return "admin/edit";
    }
}
//todo delete mapping and put mapping
