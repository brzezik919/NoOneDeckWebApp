package io.github.brzezik919.controller;

import io.github.brzezik919.model.Decklist;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.DecklistService;
import io.github.brzezik919.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
public class DecklistController {

    private final UserService userService;
    private final DecklistService decklistService;

    public DecklistController(UserService userService, DecklistService decklistService) {
        this.userService = userService;
        this.decklistService = decklistService;
    }

    @GetMapping("/createDecklist")
    public String showCreateDecklistPanel(Model model){
        model.addAttribute("decklist", new Decklist());
        return "createDecklist";
    }

    @GetMapping("/decklists")
    public String showDecklists(){
        return "decklists";
    }

    @PostMapping("/createDecklist/generate/save")
    public String generateDecklist(Authentication auth, @RequestParam("deck") MultipartFile multipartFile) throws IOException {
        if(StringUtils.cleanPath(multipartFile.getOriginalFilename()) == "") {
            return "redirect:/createDecklist";
        }
        String deck = decklistService.encodingDecklist(multipartFile);
        System.out.println(deck);
        decklistService.createDecklist(multipartFile.getOriginalFilename(), deck, auth.getName());
        return "redirect:/createDecklist";
    }
}
