package io.github.brzezik919.service;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardName;
import io.github.brzezik919.model.CardNameRepository;
import io.github.brzezik919.model.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardNameRepository cardNameRepository;

    public CardService(CardRepository cardRepository, CardNameRepository cardNameRepository) {
        this.cardRepository = cardRepository;
        this.cardNameRepository = cardNameRepository;
    }

    public List<Card> getAllStats(String name){
        return cardRepository.findByUser_Login(name);
    } //Change with Keycloak

    public List<Card> searchAllCardsNames(String name){
        return cardRepository.findByCardName_Name(name);
    }

    public List<Card> getCardsByState(String state){return cardRepository.findByState(state);}

    public CardName getCardName(String name){
        List<CardName> cardNames = this.cardNameRepository.findByName(name);
        if(!cardNames.isEmpty()){
            return cardNames.get(0);
        }
        return null;
    }

    public void save(Card card){cardRepository.save(card);}
}
