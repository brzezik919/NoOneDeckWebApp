package io.github.brzezik919.controller;


import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardRepository;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.model.projection.CardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Card result = cardRepository.save(card.newCard());
          return "redirect:/cardPanel";
    }
}