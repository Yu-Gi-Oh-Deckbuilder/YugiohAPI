package com.revature.main.controller;

import com.revature.main.exceptions.CardAmountDoesNotExistException;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.*;
import com.revature.main.service.CardAmountService;
import com.revature.main.service.DeckService;
import com.revature.main.service.WishlistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CollectionControllerTest {

    private static User user;
    private static Role role;
    private static Wishlist wishlist;
    private static Inventory inventory;
    private static Deck deck;
    private static List<CardAmount> cards;
    private static BanList banList;

    @Mock
    DeckService deckService;

    @Mock
    WishlistService wishListService;

    @Mock
    CardAmountService cardAmountService;

   // @Mock
   // InventoryService inventoryServiceService;

    @InjectMocks
    private CollectionController collectionsController;

    @BeforeAll
    public static void init(){
        role = new Role(1,"user");
        user = new User(1,"test", "testpass", "test", "test", "test@email.com", role);

        wishlist = new Wishlist();
        deck = new Deck();
        inventory = new Inventory();
        banList = new BanList();
        cards = new ArrayList<>();


        cards.add(new CardAmount());

        wishlist.setId(1);
        wishlist.setOwner(user);
        wishlist.setCards(cards);
        wishlist.setSharedUsers(new ArrayList<User>());
        wishlist.getSharedUsers().add(user);

        banList.setId(1);
        banList.setBanList("TCG");
        deck.setId(1);
        deck.setOwner(user);
        deck.setCards(cards);
        deck.setBanList(banList);

        inventory.setId(1);
        inventory.setOwner(user);
        inventory.setCards(cards);
    }

    @Test
    public void getAllCollectionsByUserId_positive() throws UserNotFoundException {
        List<Collection> allCollections = new ArrayList<>();
        List<Wishlist> allWishlist = new ArrayList<>();


        allWishlist.add(wishlist);
        allCollections.add(wishlist);

        when(wishListService.getAllWishlistByUserId(1)).thenReturn((allWishlist));
        //when(deckService.getAllDeckByUserId(1)).thenReturn((allDecks));
        //when(inventoryService.getInventoryByUserId(1)).thenReturn((inventory));
        ResponseEntity responseEntity = collectionsController.getAllCollectionsByUserId(user.getId());

        List<Collection> collectionList = (List<Collection>) responseEntity.getBody();

        assertThat(allCollections).isEqualTo(collectionList);
    }
/*
    @Test
    public void getAllCollectionsByUserId_UserNotFoundException(){
        Assertions.assertThrows(UserNotFoundException.class, ()->{
            collectionsController.getAllWishlistsByUserId(user.getId());
        });
    }*/

    @Test
    public void getAllWishlistsByUserId_positive() throws UserNotFoundException {
        List<Wishlist> allWishlist = new ArrayList<>();

        allWishlist.add(wishlist);

        when(wishListService.getAllWishlistByUserId(1)).thenReturn((allWishlist));
        ResponseEntity responseEntity = collectionsController.getAllWishlistsByUserId(user.getId());

        List<Wishlist> collectionList = (List<Wishlist>) responseEntity.getBody();

        assertThat(allWishlist).isEqualTo(collectionList);
    }


    @Test void getAllDecksByUserId_positive() throws UserNotFoundException, CollectionDoesNotExistException, UnAuthorizedException {
        List<Deck> decks = new ArrayList<>();

        decks.add(deck);
        when(deckService.getAllDecksByUserId(1)).thenReturn(decks);
        ResponseEntity responseEntity = collectionsController.getAllDecksByUserId(user.getId());

        List<Deck> deckList = (List<Deck>) responseEntity.getBody();

        assertThat(decks).isEqualTo(deckList);

    }

    /*
    @Test void getInventoryByUserId_positive(){
        when(deckService.getAllInventoryByUserId(1)).thenReturn(inventory);
        ResponseEntity responseEntity = collectionsController.getInventoryByUserId(user.getId());

        Inventory target = (Inventory) responseEntity.getBody();

        assertThat(inventory).isEqualTo(target);
    }*/

    @Test
    public void getWishlistById_positive() throws UserNotFoundException, CollectionDoesNotExistException, UnAuthorizedException {
        when(wishListService.getWishListById(1,1)).thenReturn(wishlist);

        ResponseEntity responseEntity = collectionsController.getWishlistById(user.getId(),wishlist.getId());
        Wishlist target = (Wishlist) responseEntity.getBody();

        assertThat(wishlist).isEqualTo(target);
    }

   @Test
    public void getDeckById_positive() throws UserNotFoundException, CollectionDoesNotExistException {
        when(deckService.getDeckById(1,1)).thenReturn(deck);

        ResponseEntity responseEntity = collectionsController.getDeckById(user.getId(),deck.getId());
        Deck target = (Deck) responseEntity.getBody();

        assertThat(deck).isEqualTo(target);
    }
/*
    @Test
    public void getInventoryById_positive(){
        when(inventoryService.getInventoryById(1,1)).thenReturn(inventory);

        ResponseEntity responseEntity = collectionsController.getInventoryById(user.getId(),inventory.getId());
        Inventory target = (Inventory) responseEntity.getBody();

        assertThat(invenetory).isEqualTo(target);
    }*/

    @Test
    public void deleteWishlistById_positive() throws UserNotFoundException {
        when(wishListService.deleteWishlistById(1)).thenReturn(true);
        ResponseEntity actual =  collectionsController.deleteWishlistById(1);
        assertThat((Boolean)actual.getBody()).isEqualTo(true);
    }

    @Test
    public void deleteWishlistById_negative(){
        ResponseEntity actual =  collectionsController.deleteWishlistById(1);
        assertThat((Boolean)actual.getBody()).isEqualTo(false);
    }

    @Test
    public void deleteDeckById_positive() throws UserNotFoundException {
        when(deckService.deleteDeckById(1)).thenReturn(true);
        ResponseEntity actual =  collectionsController.deleteDeck(1);
        assertThat((Boolean)actual.getBody()).isEqualTo(true);
    }

    @Test
    public void deleteDeckById_negative(){
        ResponseEntity actual =  collectionsController.deleteDeck(1);
        assertThat((Boolean)actual.getBody()).isEqualTo(false);
    }
/*
    @Test
    public void deleteInventoryById_positive() throws UserNotFoundException {
        when(inventoryService.deleteWishlistById(1)).thenReturn(true);
        ResponseEntity actual =  collectionsController.deleteInventoryById(1);
        assertThat((Boolean)actual.getBody()).isEqualTo(true);
    }

    @Test
    public void deleteInventoryById_negative(){
        ResponseEntity actual =  collectionsController.deleteInventoryById(1);
        assertThat((Boolean)actual.getBody()).isEqualTo(false);
    }*/

    @Test
    public void editWishlistById_positive() throws UserNotFoundException, CollectionDoesNotExistException, CardAmountDoesNotExistException {
        when(wishListService.editWishlist(wishlist)).thenReturn(wishlist);

        Wishlist editedWishlist = (Wishlist) collectionsController.editWishlistById(wishlist).getBody();

        assertThat(editedWishlist).isEqualTo(wishlist);
    }

    @Test
    public void editDeckById_positive() throws UserNotFoundException, CollectionDoesNotExistException {
        when(deckService.editDeck(deck)).thenReturn(deck);

        Deck editedDeck = (Deck) collectionsController.editDeck(deck).getBody();

        assertThat(editedDeck).isEqualTo(deck);
    }

    /*@Test
    public void editWishlistById_CollectionDoesNotExist() throws UserNotFoundException, CollectionDoesNotExistException {
        //when(wishListService.editWishlist(wishlist)).thenReturn(null);

        Assertions.assertThrows(CollectionDoesNotExistException.class,()->{
            collectionsController.editWishlistById(wishlist);
        });
    }*/

    @Test
    public void createWishlist_positive() throws UserNotFoundException {
        when(wishListService.createWishlist(wishlist)).thenReturn(wishlist);
        when(cardAmountService.createCardAmount(cards)).thenReturn(cards);

        Wishlist createdWishlist = (Wishlist) collectionsController.createWishlist(wishlist).getBody();

        assertThat(createdWishlist).isEqualTo(wishlist);
    }

    @Test
    public void createDeck_positive() throws UserNotFoundException {
        when(deckService.createDeck(deck)).thenReturn(deck);

        Deck createdDeck = (Deck) collectionsController.createDeck(deck).getBody();

        assertThat(createdDeck).isEqualTo(deck);
    }
}
