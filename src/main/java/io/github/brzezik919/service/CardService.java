package io.github.brzezik919.service;

import io.github.brzezik919.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Card> getAllStats(String name, int currentPage, int pageSize){
        Pageable page = PageRequest.of(currentPage, pageSize);
        return cardRepository.findByUser_Login_OrderByCardName_Name_Asc(name, page);
    }

    public Page<Card> searchAllCardsNames(String name, String login, int currentPage, int pageSize){
        Pageable page = PageRequest.of(currentPage, pageSize);
        return cardRepository.findByCardName_NameAndUser_LoginOrderByCardName_Name_Asc(name, login, page);
    }

    public Page<Card> searchAllCardInTeam(String login, int currentPage, int pageSize){
        Pageable page = PageRequest.of(currentPage, pageSize);
        User user = userRepository.findByLogin(login);
        if(user.getTeam() != null){
            return cardRepository.findByUser_Team_IdOrderByCardName_Name_Asc(user.getTeam().getId(), page);
        }
        return null;
    }

    public Page<Card> searchAllCardNamesInTeam(String name, String login, int currentPage, int pageSize){
        Pageable page = PageRequest.of(currentPage, pageSize);
        User user = userRepository.findByLogin(login);
        return cardRepository.findByCardName_NameAndUser_Team_IdOrderByCardName_Name_Asc(name, user.getTeam().getId(), page);
    }

    public Page<Card> getCardsByState(String state, int currentPage, int pageSize){
        Pageable page = PageRequest.of(currentPage, pageSize);
        return cardRepository.findByStateOrderByCardName_Name_Asc(state, page);
    }

    public Page<Card> searchAllCardNamesForSell(String name, String state, int currentPage, int pageSize){
        Pageable page = PageRequest.of(currentPage, pageSize);
        return cardRepository.findByCardName_NameAndStateOrderByCardName_Name_Asc(name, state, page);
    }

    public List<Card> searchAllCardsUserByState(int id, String state){
        return cardRepository.findByUser_IdAndState(id, state);
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

    public void delete(int userId, int id){
        Card card = cardRepository.findById(id);
        if(card.getUser().getId() == userId){
            cardRepository.deleteById(id);
        }
    }
    public void changeState(int userId, int id, String state, String note){
        Card card = cardRepository.findById(id);
        if(card.getUser().getId() == userId){
            card.setState(state);
            card.setNote(note);
            cardRepository.save(card);
        }
    }
}
