package io.github.brzezik919.controller;


import io.github.brzezik919.model.*;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cardPanel")
public class CardController {

    private final  CardService cardService;
    private final UserService userService;

    public CardController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping
    public String showCardPanel(Model model, Authentication name){
        User userLogIn = userService.getUserByName(name.getName());
        List<Card> cardList = cardService.getAllStats(name.getName());
        model.addAttribute("user", userLogIn);
        model.addAttribute("cardList", cardList);
        model.addAttribute("card", new CardModel());
        return "cardPanel";
    }

    @GetMapping("/cardSearch")
    public String cardSearchCardPanel(Model model, @ModelAttribute CardModel card, Authentication name){
        if(card.getCardName().equals("")){
            return "redirect:/cardPanel";
        }
        User userLogIn = userService.getUserByName(name.getName());
        List<Card> cardList = cardService.searchAllCardsNames(card.getCardName(), name.getName());
        if(cardList != null){
            model.addAttribute("cardList", cardList);
            model.addAttribute("user", userLogIn);
            model.addAttribute("card", new CardModel());
        }
        return "cardPanel";
    }

    @PostMapping("/addCard")
    String addCard(@ModelAttribute CardModel card, Authentication name){
        if(card.getCardName().trim().equals("")) {
            return "redirect:/cardPanel";
        }
        CardName cardNameFound = this.cardService.getCardName(card.getCardName().trim());

        if(Objects.nonNull(cardNameFound)){
            Card cardToSave = card.newCard(cardNameFound, userService.getAllUserStats(name.getName()));
            cardService.save(cardToSave);
        } else {
            return "redirect:/cardPanel";
        }
        return "redirect:/cardPanel";
    }

    @PutMapping("/cardPanelEditCard")
    String changeStateCard(@ModelAttribute CardModel card, Authentication auth){
        if(Objects.isNull(cardService.searchCardById(card.getId()))){
            return "redirect:/cardPanel";
        }
        User user = userService.getUserByName(auth.getName());
        cardService.changeState(user.getId(), card.getId(), card.getState(), card.getNote().trim());
        return "redirect:/cardPanel";
    }

    @DeleteMapping("/cardPanelDeleteCard")
    String deleteCard(@ModelAttribute CardModel card, Authentication auth){
        if(Objects.isNull(cardService.searchCardById(card.getId()))){
            return "redirect:/cardPanel";
        }
        User user = userService.getUserByName(auth.getName());
        cardService.delete(user.getId(), card.getId());
        return "redirect:/cardPanel";
    }
}