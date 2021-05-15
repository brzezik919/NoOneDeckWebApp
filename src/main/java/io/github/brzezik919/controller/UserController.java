package io.github.brzezik919.controller;

import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String Index(Principal principal){
        //model.addAttribute("user", new UserModel());
        return principal != null ? "index" : "index";
    }
}
