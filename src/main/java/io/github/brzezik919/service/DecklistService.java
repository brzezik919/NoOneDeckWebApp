package io.github.brzezik919.service;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.Decklist;
import io.github.brzezik919.model.DecklistRepository;
import io.github.brzezik919.model.UserRepository;
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
}