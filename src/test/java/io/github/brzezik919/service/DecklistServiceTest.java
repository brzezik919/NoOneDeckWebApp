package io.github.brzezik919.service;

import io.github.brzezik919.model.CardNameRepository;
import io.github.brzezik919.model.Decklist;
import io.github.brzezik919.model.DecklistRepository;
import io.github.brzezik919.model.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DecklistServiceTest {

    @Mock
    private DecklistRepository mockDecklistRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private CardNameRepository mockCardNameRepository;

    @InjectMocks
    private DecklistService decklistService;

    @Captor
    private ArgumentCaptor<Decklist> decklistArgumentCaptor = ArgumentCaptor.forClass(Decklist.class);

    @Test
    public void changePermissionDecklist_stateDeckListIsPublic(){
        //given
        Decklist decklistPublic = new Decklist();
        when(mockDecklistRepository.findById(anyInt())).thenReturn(decklistPublic);
        //when
        decklistService.changePermissionDecklist(decklistPublic.getId(), "public");
        //then
        Mockito.verify(mockDecklistRepository).save(decklistArgumentCaptor.capture());
        Decklist decklistTest = decklistArgumentCaptor.getValue();
        assertTrue(decklistTest.isPublicShared());
        assertTrue(!decklistTest.isTeamShared());
    }

    @Test
    public void changePermissionDecklist_stateDeckListIsTeam(){
        //given
        Decklist decklistPublic = new Decklist();
        when(mockDecklistRepository.findById(anyInt())).thenReturn(decklistPublic);
        //when
        decklistService.changePermissionDecklist(decklistPublic.getId(), "team");
        //then
        Mockito.verify(mockDecklistRepository).save(decklistArgumentCaptor.capture());
        Decklist decklistTest = decklistArgumentCaptor.getValue();
        assertTrue(!decklistTest.isPublicShared());
        assertTrue(decklistTest.isTeamShared());
    }

    @Test
    public void changePermissionDecklist_stateDeckListIsPrivate(){
        //given
        Decklist decklistPublic = new Decklist();
        when(mockDecklistRepository.findById(anyInt())).thenReturn(decklistPublic);
        //when
        decklistService.changePermissionDecklist(decklistPublic.getId(), "private");
        //then
        Mockito.verify(mockDecklistRepository).save(decklistArgumentCaptor.capture());
        Decklist decklistTest = decklistArgumentCaptor.getValue();
        assertTrue(!decklistTest.isPublicShared());
        assertTrue(!decklistTest.isTeamShared());
    }
}