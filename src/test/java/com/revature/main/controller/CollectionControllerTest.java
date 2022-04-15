package com.revature.main.controller;

import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.*;
import com.revature.main.service.UserService;
import com.revature.main.service.WishlistService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Assertions;

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
    private static HashMap<Integer,Integer> cards;
   // @Mock
   // DeckService deckService;

    @Mock
    WishlistService wishListService;

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

        cards = new HashMap<>();
        cards.put(1,1);
        cards.put(2,2);

        wishlist.setId(1);
        wishlist.setOwner(user);
        wishlist.setCards(cards);
        wishlist.setSharedUsers(new ArrayList<User>());
        wishlist.getSharedUsers().add(user);

        deck.setId(1);
        deck.setOwner(user);
        deck.setCards(cards);

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
}
