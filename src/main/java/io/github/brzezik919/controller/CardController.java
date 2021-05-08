package io.github.brzezik919.controller;


import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardName;
import io.github.brzezik919.model.CardRepository;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.model.projection.CardModel;
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

    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping
    public String CardPanel(Model model){
        List<Card> cardList = cardService.getAllStats();
        model.addAttribute("cardList", cardList);
        model.addAttribute("card", new CardModel());
        return "cardPanel";
    }

    @PostMapping
    String addCard(Model model, @ModelAttribute CardModel card){
        if(card.getCardName().equals("")) {
            return "redirect:/cardPanel";
        }
        CardName cardNameFound = this.cardService.getCardName(card.getCardName());

        if(Objects.nonNull(cardNameFound)){
            Card cardToSave = card.newCard(cardNameFound);
            cardRepository.save(cardToSave);
        } else {
            // Return error to user
            return "redirect:/cardPanel";
        }
        return "redirect:/cardPanel";
    }
}