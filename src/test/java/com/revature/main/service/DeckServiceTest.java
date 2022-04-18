package com.revature.main.service;

import com.revature.main.dao.DeckRepository;
import com.revature.main.dao.UserRepository;
import com.revature.main.dto.DeckDto;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckServiceTest {

    @Mock
    DeckRepository deckRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    DeckService deckService;

    private static User user;
    private static Deck deck;
    private static Deck deck2;
    private static DeckDto deckDto;

    @BeforeAll
    public static void init (){
        Role role = new Role(1, "user");
        user = new User(1,"test", "testpass", "test", "test", "test@email.com", role);
        User user2 = new User(2, "test2", "testpass", "test", "test", "test@email.com", role);
        deck = new Deck();
        deck2 = new Deck();
        deckDto = new DeckDto();

        List<CardAmount> cards = new ArrayList<>();
        cards.add(new CardAmount());

        BanList banList = new BanList(1, "TCG");

        deck.setId(1);
        deck.setOwner(user);
        deck.setCards(cards);
        deck.setBanList(banList);

        deck2.setId(2);
        deck2.setOwner(user);
        deck2.setCards(cards);
        deck2.setBanList(banList);

        deckDto.setId(deck.getId());
        deckDto.setOwner(deck.getOwner());
        deckDto.setCards(deck.getCards());
        deckDto.setBanList(deck.getBanList());
    }

    @Test
    public void getAllDecks() {
        List<Deck> decks = new ArrayList<>();
        decks.add(deck);
        decks.add(deck2);

        when(deckRepository.findAll()).thenReturn(decks);
        List<Deck> actual = deckService.getAllDecks();

        assertThat(actual).isEqualTo(decks);
    }

    @Test
    public void getAllDecksByUserId_positive() throws UserNotFoundException, CollectionDoesNotExistException {
        List<Deck> decks = new ArrayList<>();
        decks.add(deck);
        decks.add(deck2);

        when(deckRepository.findAllByUserId(1)).thenReturn(decks);
        when(userRepository.existsById(1)).thenReturn(true);
        List<Deck> actual = deckService.getAllDecksByUserId(1);

        assertThat(actual).isEqualTo(decks);
    }

    @Test
    public void getAllWishlistByUserId_UserNotExist() {
        Assertions.assertThrows(UserNotFoundException.class,  () ->{
            deckService.getAllDecksByUserId(1);
        });
    }

    @Test
    public void getDeckById_positive() throws UserNotFoundException, CollectionDoesNotExistException {
        when(deckRepository.findById(1)).thenReturn(Optional.of(deck));
        when(userRepository.existsById(1)).thenReturn(true);
        Deck actual = deckService.getDeckById(1,user.getId());

        assertThat(actual).isEqualTo(deck);
    }


    @Test
    public void getDeckById_UserNotFoundException(){
        Assertions.assertThrows(UserNotFoundException.class,  () ->{
            deckService.getDeckById(1,user.getId());
        });
    }

    @Test
    public void getDeckById_CollectionDoesNotExistException(){
        when(userRepository.existsById(1)).thenReturn(true);

        Assertions.assertThrows(CollectionDoesNotExistException.class,  () ->{
            deckService.getDeckById(1,user.getId());
        });
    }

    @Test
    public void editDeck_positive() throws UserNotFoundException, CollectionDoesNotExistException {

        when(deckRepository.findById(1)).thenReturn(Optional.of(deck));
        when(userRepository.existsById(deck.getOwner().getId())).thenReturn(true);
        Deck expected = new Deck();
        expected.setCards(deck.getCards());
        expected.setOwner(deck.getOwner());
        expected.setId(deck.getId());
        expected.setBanList(deck.getBanList());

        when(deckRepository.saveAndFlush(expected)).thenReturn(expected);
        Deck actual = deckService.editDeck(deckDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test public void editDeck_CollectionDoesNotExistException(){
        when(userRepository.existsById(deckDto.getOwner().getId())).thenReturn(true);
        Assertions.assertThrows(CollectionDoesNotExistException.class,()->{
            deckService.editDeck(deckDto);
        });
    }

    @Test public void editDeck_UserNotFoundException(){
        Assertions.assertThrows(UserNotFoundException.class,()->{
            deckService.editDeck(deckDto);
        });
    }

    @Test
    public void deleteDeck_positive(){
        when(deckRepository.existsById(1)).thenReturn(true);
        boolean actual = deckService.deleteDeckById(1);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void deleteDeck_negative(){
        boolean actual = deckService.deleteDeckById(1);
        assertThat(actual).isEqualTo(false);
    }

    @Test
    public void createDeck_positive() throws UserNotFoundException {
        Deck newDeck = new Deck();
        newDeck.setName(deck.getName());
        newDeck.setOwner(deck.getOwner());
        newDeck.setBanList(deck.getBanList());
        newDeck.setCards(deck.getCards());
        when(userRepository.existsById(deckDto.getOwner().getId())).thenReturn(true);
        when(deckRepository.saveAndFlush(newDeck)).thenReturn(newDeck);
        Deck actual = deckService.createDeck(deckDto);
        assertThat(actual).isEqualTo(newDeck);
    }

    @Test
    public void createDeck_UserNotFoundException(){
        Assertions.assertThrows(UserNotFoundException.class, ()->{
            deckService.createDeck(deckDto);
        });
    }
}
