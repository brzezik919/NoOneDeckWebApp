package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardRepository;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cardSearch")
public class CardSearchController {
    @Autowired
    CardService cardService;

    @Autowired
    UserService userService;

    @GetMapping
    String CardPanel(Model model, Principal name){
        model.addAttribute("cardList", null);
        model.addAttribute("card", new CardModel());
        model.addAttribute("user", userService.getUserByName(name.getName()));
        return "cardSearch";
    }

    @PostMapping
    String searchCard(Model model, @ModelAttribute CardModel card, Principal name){
        if(card.getCardName().equals("") || userService.getUserByName(name.getName()).getTeam() == null){
            return "redirect:/cardSearch";
        }
        List<Card> cardList = cardService.searchAllCardNamesInTeam(card.getCardName(), name.getName());
        if(cardList != null){
            model.addAttribute("cardList", cardList);
            model.addAttribute("user", userService.getUserByName(name.getName()));
            model.addAttribute("card", new CardModel());
            System.out.println(userService.getUserByName(name.getName()).getTeam().getId());
        }
        return "cardSearch";
    }
}
