package io.github.brzezik919.controller;


import io.github.brzezik919.model.*;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public String CardPanel(Model model, Principal name){

        User userLogIn = userService.getUserByName(name.getName());
        if(Objects.isNull(userLogIn)){
            User userToCreate = new User();
            userToCreate.setLogin(name.getName());
            userService.save(userToCreate);
        }
        List<Card> cardList = cardService.getAllStats(name.getName());
        model.addAttribute("user", userLogIn);
        model.addAttribute("cardList", cardList);
        model.addAttribute("card", new CardModel());
        return "cardPanel";
    }

    @GetMapping("/cardSearch")
    public String CardPanelCardSearch(Model model, @ModelAttribute CardModel card, Principal name){
        if(card.getCardName().equals("")){
            return "redirect:/cardPanel";
        }
        List<Card> cardList = cardService.searchAllCardsNames(card.getCardName(), name.getName());
        if(cardList != null){
            model.addAttribute("cardList", cardList);
            model.addAttribute("user", new UserModel());
            model.addAttribute("card", new CardModel());
        }
        return "cardPanel";
    }

    @PostMapping
    String addCard(@ModelAttribute CardModel card, Principal name){
        if(card.getCardName().equals("")) {
            return "redirect:/cardPanel";
        }
        CardName cardNameFound = this.cardService.getCardName(card.getCardName());

        if(Objects.nonNull(cardNameFound)){
            Card cardToSave = card.newCard(cardNameFound, userService.getAllUserStats(name.getName()));
            cardService.save(cardToSave);
        } else {
            return "redirect:/cardPanel";
        }
        return "redirect:/cardPanel";
    }

    @PutMapping
    String changeStateCard(@PathVariable int id, @PathVariable String state, @ModelAttribute CardModel card){
        if(Objects.isNull(cardService.searchCardById(id))){
            return "redirect:/cardPanel";
        }
        cardService.changeState(id, state);
        return "redirect:/cardPanel";
    }

    @DeleteMapping
    String deleteCard(@PathVariable int id, @ModelAttribute CardModel card){
        if(Objects.isNull(cardService.searchCardById(id))){
            return "redirect:/cardPanel";
        }
        cardService.delete(id);
        return "redirect:/cardPanel";
    }

}