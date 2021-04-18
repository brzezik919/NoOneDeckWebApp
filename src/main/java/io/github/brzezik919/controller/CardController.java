package io.github.brzezik919.controller;

import io.github.brzezik919.object.card.CardRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/cards")
public class CardController {
    private final CardRepository repository;

    public CardController(CardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getAllUserCards(@PathVariable String id){
        return ResponseEntity.ok(repository.findByIdUser(Integer.parseInt(id)));
    }
}
