package io.github.brzezik919.controller;


import io.github.brzezik919.model.*;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cardPanel")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    UserService userService;

    @GetMapping
    public String CardPanel(Model model){
        List<Card> cardList = cardService.getAllStats();
        model.addAttribute("cardList", cardList);
        model.addAttribute("card", new CardModel());
        return "cardPanel";
    }

    @PostMapping
    String addCard(@ModelAttribute CardModel card){
        if(card.getCardName().equals("")) {
            return "redirect:/cardPanel";
        }
        CardName cardNameFound = this.cardService.getCardName(card.getCardName());
        User user = this.userService.getUserById(1);

        if(Objects.nonNull(cardNameFound)){
            Card cardToSave = card.newCard(cardNameFound, user);
            cardService.save(cardToSave);
        } else {
            return "redirect:/cardPanel";
        }
        return "redirect:/cardPanel";
    }
}