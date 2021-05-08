package io.github.brzezik919.controller;

import io.github.brzezik919.model.UserRepository;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String Index(){return "index";}

    /*@PostMapping
    String LogIn(Model model, @ModelAttribute UserModel user){
        if(user.getLogin().equals("") || user.getPassword().equals(""))
            return "redirect:/";
    }*/
}