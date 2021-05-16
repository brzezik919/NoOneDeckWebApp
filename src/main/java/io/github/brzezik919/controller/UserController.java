package io.github.brzezik919.controller;

import io.github.brzezik919.model.User;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;


@Controller
@RequestMapping
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String Index(Principal principal){
        return principal != null ? "index" : "redirect:/login";
    }

    @GetMapping("/login")
    public String Login(Authentication auth){
        User userLogIn = userService.getUserByName(auth.getName());
        if(Objects.isNull(userLogIn)){
            User userToCreate = new User();
            userToCreate.setLogin(auth.getName());
            userToCreate.setNickname("Unknown");
            userService.save(userToCreate);
        }
        return "/index";
    }
}
