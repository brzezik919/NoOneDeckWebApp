package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardRepository;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cardSearch")
public class CardSearchController {
    @Autowired
    CardService cardService;

    @GetMapping
    String CardPanel(Model model){
        model.addAttribute("cardList", null);
        model.addAttribute("card", new CardModel());
        return "cardSearch";
    }

    @PostMapping
    String searchCard(Model model, @ModelAttribute CardModel card){
        if(card.getCardName().equals("")){
            return "redirect:/cardSearch";
        }
        List<Card> cardList = cardService.searchAllCardsNames(card.getCardName());
        if(cardList != null){
            model.addAttribute("cardList", cardList);
            model.addAttribute("user", new UserModel());
            model.addAttribute("card", new CardModel());
        }
        return "cardSearch";
    }
}
