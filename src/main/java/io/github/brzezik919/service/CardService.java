package io.github.brzezik919.service;

import io.github.brzezik919.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardNameRepository cardNameRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository, CardNameRepository cardNameRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.cardNameRepository = cardNameRepository;
    }

    public List<Card> getAllStats(String name){
        return cardRepository.findByUser_Login(name);
    }

    public List<Card> searchAllCardsNames(String name, String login){
        return cardRepository.findByCardName_NameAndUser_Login(name, login);
    }

    public List<Card> searchAllCardNamesInTeam(String name, String login){
        User user = userRepository.findByLogin(login);
        return cardRepository.findByCardName_NameAndUser_Team_Id(name, user.getTeam().getId());
    }

    public List<Card> getCardsByState(String state){return cardRepository.findByState(state);}

    public List<Card> searchAllCardNamesForSell(String name, String state){
        return cardRepository.findByCardName_NameAndState(name, state);
    }

    public CardName getCardName(String name){
        List<CardName> cardNames = this.cardNameRepository.findByName(name);
        if(!cardNames.isEmpty()){
            return cardNames.get(0);
        }
        return null;
    }

    public Card searchCardById(int id){
        return cardRepository.findById(id);
    }

    public void save(Card card){cardRepository.save(card);}
    public void delete(int id){cardRepository.deleteById(id);}
    public void changeState(int id, String state, String note){
        Card card = cardRepository.findById(id);
        card.setState(state);
        card.setNote(note);
        cardRepository.save(card);
    }
}
