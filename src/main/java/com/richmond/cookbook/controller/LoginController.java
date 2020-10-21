package com.richmond.cookbook.controller;


import com.richmond.cookbook.entity.User;
import com.richmond.cookbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping(value="/signup", method=RequestMethod.GET)
    public String registration(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public String createNewUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap model) {
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult.rejectValue("userName", "error.user", "*User name already exists");
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.saveNewUser(user, "USER");
        model.addAttribute("successMessage", "User has been created successfully");
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }
}
