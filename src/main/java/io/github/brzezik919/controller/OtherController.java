package io.github.brzezik919.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {

    @GetMapping("/statute")
    String showStatute(){ return "statute"; }

    @GetMapping("/about")
    String showAboutUs(){
        return "about";
    }

    @GetMapping("/contact")
    String showContact(){
        return "contact";
    }
}