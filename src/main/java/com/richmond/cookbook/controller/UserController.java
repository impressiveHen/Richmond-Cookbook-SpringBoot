package com.richmond.cookbook.controller;

import com.richmond.cookbook.entity.User;
import com.richmond.cookbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/admin/profile", method= RequestMethod.GET)
    public String adminHome(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("adminMessage","Content Available Only for Users with Admin Role");
        return "admin/profile";
    }

    @RequestMapping(value="/user/profile", method=RequestMethod.GET)
    public String userHome(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("listRecipes", user.getRecipes());
        return "user/profile";
    }
}
