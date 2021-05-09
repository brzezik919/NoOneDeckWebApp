package io.github.brzezik919.controller;

import io.github.brzezik919.model.User;
import io.github.brzezik919.model.UserRepository;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.UserService;
import org.apache.tomcat.jni.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping
public class UserController {

    private User userLogin = new User();

    @Autowired
    UserService userService;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String Index(Model model){
        model.addAttribute("user", new UserModel());
        return "index";
    }

    @PostMapping("/index")
    String LogIn(Model model, @ModelAttribute UserModel user){
        if(user.getLogin().equals("") || user.getPassword().equals("")) {
            return "redirect:";
        }
        User userFound = this.userService.getAllUserStats(user.getLogin(), user.getPassword());
        if(Objects.nonNull(userFound)){
            userLogin.setId(userFound.getId());
            userLogin.setLogin(userFound.getLogin());
            userLogin.setRole(userFound.getRole());
        }
        return "redirect:";
    }
}
