package io.github.brzezik919.service;

import io.github.brzezik919.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class DecklistService {

    private final UserRepository userRepository;
    private final DecklistRepository decklistRepository;

    public DecklistService(UserRepository userRepository, DecklistRepository decklistRepository) {
        this.userRepository = userRepository;
        this.decklistRepository = decklistRepository;
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
}