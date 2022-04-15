package com.revature.main.service;

import com.revature.main.dao.DeckRepository;
import com.revature.main.dao.UserRepository;
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
    private static User user2;
    private static  Role role;
    private static Deck deck;
    private static Deck deck2;
    private static HashMap<Integer,Integer> cards;
    private static BanList banList;

    @BeforeAll
    public static void init (){
        role = new Role(1,"user");
        user = new User(1,"test", "testpass", "test", "test", "test@email.com", role);
        user2 =  new User(2,"test2", "testpass", "test", "test", "test@email.com", role);
        deck = new Deck();
        deck2 = new Deck();

        cards = new HashMap<>();
        cards.put(1,1);
        cards.put(2,2);

        banList = new BanList(1, "TCG");

        deck.setId(1);
        deck.setOwner(user);
        deck.setCards(cards);
        deck.setBanList(banList);

        deck2.setId(2);
        deck2.setOwner(user);
        deck2.setCards(cards);
        deck2.setBanList(banList);
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
    public void getAllDecksByUserId_positive() throws UserNotFoundException, CollectionDoesNotExistException, UnAuthorizedException {
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
        when(deckRepository.getById(1)).thenReturn(deck);
        when(deckRepository.existsById(1)).thenReturn(true);
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

        when(deckRepository.getById(1)).thenReturn(deck);
        when(deckRepository.existsById(deck.getId())).thenReturn(true);
        when(userRepository.existsById(deck.getOwner().getId())).thenReturn(true);
        Deck expected = new Deck();
        expected.setCards(deck.getCards());
        expected.setOwner(deck.getOwner());
        expected.setId(deck.getId());

        Deck actual = deckService.editDeck(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test public void editDeck_CollectionDoesNotExistException(){
        Assertions.assertThrows(CollectionDoesNotExistException.class,()->{
            deckService.editDeck(deck);
        });
    }

    @Test public void editDeck_UserNotFoundException(){
        when(deckRepository.existsById(deck.getId())).thenReturn(true);

        Assertions.assertThrows(UserNotFoundException.class,()->{
            deckService.editDeck(deck);
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
        when(deckRepository.save(deck)).thenReturn(deck);
        when(userRepository.existsById(deck.getOwner().getId())).thenReturn(true);
        Deck actual = deckService.createDeck(deck);
        assertThat(actual).isEqualTo(deck);
    }

    @Test
    public void createDeck_UserNotFoundException(){
        Assertions.assertThrows(UserNotFoundException.class, ()->{
            deckService.createDeck(deck);
        });
    }
}
