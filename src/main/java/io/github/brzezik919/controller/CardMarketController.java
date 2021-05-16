package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/market")
public class CardMarketController {
    @Autowired
    CardService cardService;

    @GetMapping
    public String CardMarket(Model model){
        List<Card> cardList = cardService.getCardsByState("For sell");
        if(cardList != null){
            model.addAttribute("cardList", cardList);
            model.addAttribute("user", new UserModel());
            model.addAttribute("card", new CardModel());
        }
        return "market";
    }

    @GetMapping("/cardSearch")
    public String CardMarketSearch(Model model, @ModelAttribute CardModel card){
        if(card.getCardName().equals("")){
            return "redirect:/market";
        }
        List<Card> cardList = cardService.searchAllCardNamesForSell(card.getCardName(), "For sell");
        if(cardList != null){
            model.addAttribute("cardList", cardList);
            model.addAttribute("user", new UserModel());
            model.addAttribute("card", new CardModel());
        }
        return "market";
    }
}
