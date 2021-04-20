package io.github.brzezik919.controller;


import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardRepository;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.model.projection.CardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "cardPanel";
    }

    @PostMapping(params = "addCardForm")
    ResponseEntity<Card> addCard(@RequestBody CardModel card){
        Card result = cardRepository.save(card.newCard());
        return ResponseEntity.ok().build();
    }
}