package io.github.brzezik919.controller;

import io.github.brzezik919.model.*;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/market")
public class CardMarketController {

    @Autowired
    CardService cardService;

    @Autowired
    UserService userService;

    @GetMapping
    public String CardMarket(Model model, Authentication name){
        User userLogIn = userService.getUserByName(name.getName());
        List<Card> cardList = cardService.getCardsByState(State.forSell.toString());
        if(cardList != null){
            model.addAttribute("cardList", cardList);
            model.addAttribute("user", new UserModel());
            model.addAttribute("userLogIn", userLogIn);
            model.addAttribute("card", new CardModel());
            model.addAttribute("transaction", new Transaction());
        }
        return "market";
    }

    @GetMapping("/cardSearch")
    public String CardMarketSearch(Model model, @ModelAttribute CardModel card, Authentication name){
        User userLogIn = userService.getUserByName(name.getName());
        if(card.getCardName().equals("")){
            return "redirect:/market";
        }
        List<Card> cardList = cardService.searchAllCardNamesForSell(card.getCardName(), State.forSell.toString());
        if(cardList != null){
            model.addAttribute("userLogIn", userLogIn);
            model.addAttribute("cardList", cardList);
            model.addAttribute("user", new UserModel());
            model.addAttribute("card", new CardModel());
            model.addAttribute("transaction", new Transaction());
        }
        return "market";
    }
}
