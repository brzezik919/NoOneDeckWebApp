package io.github.brzezik919.service;

import io.github.brzezik919.model.*;
import io.github.brzezik919.model.projection.CardDecklistModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class DecklistService {

    private final UserRepository userRepository;
    private final DecklistRepository decklistRepository;
    private final CardNameRepository cardNameRepository;

    public DecklistService(UserRepository userRepository, DecklistRepository decklistRepository, CardNameRepository cardNameRepository) {
        this.userRepository = userRepository;
        this.decklistRepository = decklistRepository;
        this.cardNameRepository = cardNameRepository;
    }

    public void createDecklist(String name, String deck, String login){
        Decklist decklistToSave = new Decklist();
        decklistToSave.setUser(userRepository.findByLogin(login));
        decklistToSave.setName(name);
        decklistToSave.setDeck(deck);
        decklistRepository.save(decklistToSave);
    }

    public String encodingDecklist(MultipartFile decklist) throws IOException {
        String deck = new String(decklist.getBytes());
        return Base64.getEncoder().encodeToString(deck.getBytes());
    }

    public String decodingDecklist(String decklist){
        byte[] decodedBytes = Base64.getDecoder().decode(decklist);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

    public Page<Decklist> getAllUserDecklist(int id, int currentPage, int pageSize){
        Pageable page = PageRequest.of(currentPage, pageSize);
        return decklistRepository.findByUser_IdOrderByNameDesc(id, page);
    }

    public Page<Decklist> getAllTeamDecklist(Team team, int currentTeamPage, int pageSize){
        Pageable page = PageRequest.of(currentTeamPage, pageSize);
        if(team != null){
            return decklistRepository.findByUser_Team_IdAndTeamSharedOrderByNameDesc(team.getId(), true, page);
        }
        return null;
    }

    public Page<Decklist> getAllPublicDecklist(int currentPublicPage, int pageSize){
        Pageable page = PageRequest.of(currentPublicPage, pageSize);
        return decklistRepository.findByPublicSharedOrderByNameDesc(true, page);
    }

    public Decklist getDecklistById(int id){
        return decklistRepository.findById(id);
    }

    public boolean getPermissionToDecklist(String userLogInLogin, int idDecklist){
        Decklist decklist = decklistRepository.findById(idDecklist);
        User userLogIn = userRepository.findByLogin(userLogInLogin);

        if(!decklist.isPublicShared()){
            if(decklist.isTeamShared() && decklist.getUser().getTeam() == userLogIn.getTeam() || decklist.getUser() == userLogIn){
               return true;
            }
            return false;
        }
        return true;
    }

    public void deleteById(int id){
        decklistRepository.deleteById(id);
    }

    public List<CardDecklistModel> getCountCardDecklist(String deck){
        List<String> newCards = Arrays.asList(deck.split("\n"));
        List<String> cards = new ArrayList<>();
        String type = null;
        for (String x : newCards){
            cards.add(x);
        }
        List<CardDecklistModel> decklist = new ArrayList<>();

        for(int i= 0; i<cards.size();) {
            if(cards.size() == 0){
                break;
            }
            String tmp = null;

            if(cards.get(i).equals("#created by ...") || cards.get(i).equals("#main") || cards.get(i).equals("#extra") || cards.get(i).equals("!side")){
                if(cards.get(i).equals("!side")){
                    type = "side";
                }
                cards.remove(i);
            }
            if (cards.size()>0 && cards.get(i).equals("#main")){
                continue;
            }
            else {
                if(cards.size()>0)
                    tmp = cards.get(i);

                int j=0, count = 0;
                boolean conditional = true;
                while(conditional) {
                    if(cards.size()>0 && cards.get(j).equals(tmp)){
                        count++;
                        cards.remove(j);
                    }
                    else {
                        j++;
                    }
                    if(j+1 > cards.size()) {
                        conditional = false;
                        i=0;
                    }
                }
                if(tmp != null){
                    CardName cardname = cardNameRepository.findById(Integer.parseInt(tmp));
                    CardDecklistModel cardDecklistModel = new CardDecklistModel();
                    cardDecklistModel.setId(cardname.getId());
                    cardDecklistModel.setName(cardname.getName());
                    if (type != null) {
                        cardDecklistModel.setType(type);
                    } else {
                        cardDecklistModel.setType(cardname.getCardType());
                    }
                    cardDecklistModel.setCount(count);
                    decklist.add(cardDecklistModel);
                }
            }
        }
        return decklist;
    }

    public void changePermissionDecklist(int id, String value){
        Decklist decklistToChange = decklistRepository.findById(id);
        if(value.equals("public")){
            decklistToChange.setPublicShared(true);
            decklistToChange.setTeamShared(false);
        }
        else if(value.equals("team")){
            decklistToChange.setPublicShared(false);
            decklistToChange.setTeamShared(true);
        }
        else{
            decklistToChange.setPublicShared(false);
            decklistToChange.setTeamShared(false);
        }
        decklistRepository.save(decklistToChange);
    }
}