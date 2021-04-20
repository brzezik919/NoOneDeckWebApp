package io.github.brzezik919.service;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    private List<Card> cardList = new ArrayList<>();
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllStats(){
        return cardRepository.findByIdUser(1);
    }


}
