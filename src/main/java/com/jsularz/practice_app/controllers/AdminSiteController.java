package com.jsularz.practice_app.controllers;

import com.jsularz.practice_app.dto.UserCreateFormDto;
import com.jsularz.practice_app.dto.UserUpdateFormDto;
import com.jsularz.practice_app.exceptions.EmailExistsException;
import com.jsularz.practice_app.exceptions.UserNotExistsException;
import com.jsularz.practice_app.models.Role;
import com.jsularz.practice_app.services.RoleService;
import com.jsularz.practice_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class AdminSiteController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @GetMapping("/add")
    private String showRegistrationForm(final Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserCreateFormDto());
        }
        model.addAttribute("roles", roleService.findAll());
        return "admin/add";
    }

    @PostMapping("/add")
    private String registerUserAccount(@ModelAttribute("user") @Valid final UserCreateFormDto accountDto,@ModelAttribute("roles") final HashSet<Role> roles,
                                       final BindingResult result, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", accountDto);
            redirectAttributes.addFlashAttribute("roles",roles);
            return "redirect:/admin/add";
        }
        try {
            userService.createNewUserAccount(accountDto);
            return "redirect:/admin/console";
        } catch (EmailExistsException e) {
            return "/admin/error";
        }
    }

    @GetMapping("/console")
    private String getUsers(final Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/console";
    }

    @DeleteMapping("/delete/{id}")
    private String deleteUser(@PathVariable("id")final Long id) {
        try {
            userService.deleteById(id);
        } catch (UserNotExistsException e) {
            return "redirect:/admin/error";
        }
        return "redirect:/admin/console";
    }

    @PutMapping("/edit/{id}")
    private String updateUser(@Valid @ModelAttribute("user") final UserUpdateFormDto user, final BindingResult result,
                              final RedirectAttributes redirectAttributes) {
        System.out.println(result.getFieldErrors());
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/admin/edit/" + user.getId();
        }
        this.userService.updateUser(user.getId(), user);
        return "redirect:/admin/console";
    }

    @GetMapping("/edit/{id}")
    private String transferUserToEdit(@PathVariable("id")final Long id, final Model model) {
        final UserCreateFormDto user = new UserCreateFormDto(userService.findById(id));
        final Iterable<Role> roles = roleService.findAll();
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
        }
        return "admin/edit";
    }
}
//todo delete mapping and put mapping
