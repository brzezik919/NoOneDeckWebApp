package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardRepository;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cardSearch")
public class CardSearchController {
    @Autowired
    CardService cardService;

    private final CardRepository cardRepository;

    public CardSearchController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping
    public String CardPanel(Model model){
        return "cardSearch";
    }

    /*@GetMapping(params = "/{cardName}")
    ResponseEntity<Card> searchCard(Model model, @ModelAttribute CardModel cardModel){

    }*/


}