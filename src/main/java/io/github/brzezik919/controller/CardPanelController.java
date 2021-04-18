package io.github.brzezik919.controller;


import io.github.brzezik919.object.card.Card;
import io.github.brzezik919.object.card.CardRepository;
import io.github.brzezik919.object.card.CardService;
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
public class CardPanelController {

    @Autowired
    CardService cardService;

    private final CardRepository cardRepository;

    public CardPanelController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping
    public String CardPanel(Model model){
        List<Card> cardList = cardService.getAllStats();
        model.addAttribute("cardList", cardList);
        return "cardPanel";
    }

    @PostMapping(params = "addCardForm")
    ResponseEntity<Card> addCard(@RequestBody Card toCreate){
        Card result = cardRepository.save(toCreate);
        return ResponseEntity.ok().build();
    }
}