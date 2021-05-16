package io.github.brzezik919.controller;

import io.github.brzezik919.model.Team;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/yourProfile")
public class UserProfileController {

    @Autowired
    UserService userService;

    @GetMapping
    String getYouProfile(Model model, Principal name){
        User userLogIn = userService.getUserByName(name.getName());
        model.addAttribute("user", userLogIn);
        return "yourProfile";
    }

    @PutMapping("/setUsername")
    String getUserNickname(@ModelAttribute User user, Authentication auth){
        if(user.getNickname().equals("")) {
            return "redirect:/yourProfile";
        }
        userService.userSetNickname(user.getNickname(), auth.getName());
        return "redirect:/yourProfile";

    }
}
