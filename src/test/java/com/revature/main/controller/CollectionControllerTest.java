package com.revature.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.main.dto.DeckDto;
import com.revature.main.dto.InventoryDto;
import com.revature.main.dto.UserDto;
import com.revature.main.dto.WishlistDto;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.*;
import com.revature.main.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CollectionControllerTest {

    private static User user;
    private static Wishlist wishlist;
    private static Inventory inventory;
    private static Deck deck;
    private static List<CardAmount> cards;
    private static BanList banList;
    private static String token;
    private static String jwt;
    private static UserDto userDto;
    private static WishlistDto wishlistDto;
    private static DeckDto deckDto;
    private static InventoryDto inventoryDto;

    @Mock
    DeckService deckService;

    @Mock
    WishlistService wishListService;

    @Mock
    CardAmountService cardAmountService;

    @Mock
    BanListService banListService;

    @Mock
    InventoryService inventoryService;

    @Mock
    JWTService jwtService;

    @InjectMocks
    private CollectionController collectionsController;

    @BeforeAll
    public static void init(){
        Role role = new Role(1, "user");
        user = new User(1,"test", "testpass", "test", "test", "test@email.com", role);

        wishlist = new Wishlist();
        deck = new Deck();
        inventory = new Inventory();
        banList = new BanList();
        cards = new ArrayList<>();
        inventoryDto = new InventoryDto();
        deckDto = new DeckDto();
        wishlistDto = new WishlistDto();

        cards.add(new CardAmount());

        wishlist.setId(1);
        wishlist.setOwner(user);
        wishlist.setCards(cards);
        wishlist.setSharedUsers(new ArrayList<>());
        wishlist.getSharedUsers().add(user);

        banList.setId(1);
        banList.setType("TCG");
        deck.setId(1);
        deck.setOwner(user);
        deck.setCards(cards);
        deck.setBanList(banList);

        inventory.setId(1);
        inventory.setOwner(user);
        inventory.setCards(cards);

        token = "Bearer jwt";
        jwt = token.split(" ")[1];

        userDto = new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserRole());

        inventoryDto.setId(inventory.getId());
        inventoryDto.setOwner(inventory.getOwner());
        inventoryDto.setCards(inventory.getCards());

        deckDto.setId(deck.getId());
        deckDto.setOwner(deck.getOwner());
        deckDto.setCards(deck.getCards());
        deckDto.setBanList(deck.getBanList());

        wishlistDto.setId(wishlist.getId());
        wishlistDto.setOwner(wishlist.getOwner());
        wishlistDto.setCards(wishlist.getCards());
        wishlistDto.setSharedUsers(wishlist.getSharedUsers());
    }


    @Test
    public void getAllWishlistsByUserId_positive() throws UserNotFoundException, JsonProcessingException {
        List<Wishlist> allWishlist = new ArrayList<>();

        allWishlist.add(wishlist);

        when(wishListService.getAllWishlistByUserId(1)).thenReturn((allWishlist));
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);
        ResponseEntity<?> responseEntity = collectionsController.getAllWishlistsByUserId(user.getId(), token);

        List<Wishlist> collectionList = (List<Wishlist>) responseEntity.getBody();

        assertThat(allWishlist).isEqualTo(collectionList);
    }


    @Test void getAllDecksByUserId_positive() throws UserNotFoundException, CollectionDoesNotExistException, JsonProcessingException {
        List<Deck> decks = new ArrayList<>();

        decks.add(deck);
        when(deckService.getAllDecksByUserId(1)).thenReturn(decks);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);
        ResponseEntity<?> responseEntity = collectionsController.getAllDecksByUserId(user.getId(), token);

        List<Deck> deckList = (List<Deck>) responseEntity.getBody();

        assertThat(decks).isEqualTo(deckList);

    }


    @Test void getInventoryByUserId_positive() throws UserNotFoundException, JsonProcessingException {
        when(inventoryService.getAllCardsInInventoryByUserId(1)).thenReturn(inventory);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);
        ResponseEntity<?> responseEntity = collectionsController.getInventoryByUserId(user.getId(), token);

        Inventory target = (Inventory) responseEntity.getBody();

        assertThat(inventory).isEqualTo(target);
    }

    @Test
    public void getWishlistById_positive() throws UserNotFoundException, CollectionDoesNotExistException, UnAuthorizedException, JsonProcessingException {
        when(wishListService.getWishListById(1,1)).thenReturn(wishlist);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);

        ResponseEntity<?> responseEntity = collectionsController.getWishlistById(user.getId(),wishlist.getId(),token);
        Wishlist target = (Wishlist) responseEntity.getBody();

        assertThat(wishlist).isEqualTo(target);
    }

   @Test
    public void getDeckById_positive() throws UserNotFoundException, CollectionDoesNotExistException, JsonProcessingException {
        when(deckService.getDeckById(1,1)).thenReturn(deck);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);

        ResponseEntity<?> responseEntity = collectionsController.getDeckById(user.getId(),deck.getId(),token);
        Deck target = (Deck) responseEntity.getBody();

        assertThat(deck).isEqualTo(target);
    }

    @Test
    public void deleteWishlistById_positive() throws JsonProcessingException {
        when(wishListService.deleteWishlistById(1)).thenReturn(true);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);
        ResponseEntity<?> actual =  collectionsController.deleteWishlistById(1, 1, token);
        assertThat((Boolean)actual.getBody()).isEqualTo(true);
    }

    @Test
    public void deleteWishlistById_negative() throws JsonProcessingException {
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);
        ResponseEntity<?> actual =  collectionsController.deleteWishlistById(1, 1, token);
        assertThat((Boolean)actual.getBody()).isEqualTo(false);
    }

    @Test
    public void deleteDeckById_positive() throws JsonProcessingException {
        when(deckService.deleteDeckById(1)).thenReturn(true);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);
        ResponseEntity<?> actual =  collectionsController.deleteDeck(1, 1, token);
        assertThat((Boolean)actual.getBody()).isEqualTo(true);
    }

    @Test
    public void deleteDeckById_negative() throws JsonProcessingException {
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);
        ResponseEntity<?> actual =  collectionsController.deleteDeck(1, 1, token);
        assertThat((Boolean)actual.getBody()).isEqualTo(false);
    }

    @Test
    public void editWishlistById_positive() throws UserNotFoundException, CollectionDoesNotExistException, JsonProcessingException {


        when(wishListService.editWishlist(wishlistDto)).thenReturn(wishlist);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);

        Wishlist editedWishlist = (Wishlist) collectionsController.editWishlistById(wishlistDto, token).getBody();

        assertThat(editedWishlist).isEqualTo(wishlist);
    }

    @Test
    public void editDeckById_positive() throws UserNotFoundException, CollectionDoesNotExistException, JsonProcessingException {
        when(deckService.editDeck(deckDto)).thenReturn(deck);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);

        Deck editedDeck = (Deck) collectionsController.editDeck(deckDto, token).getBody();

        assertThat(editedDeck).isEqualTo(deck);
    }

    @Test
    public void editInventory_positive() throws UserNotFoundException, CollectionDoesNotExistException, JsonProcessingException {
        when(inventoryService.editInventory(inventoryDto)).thenReturn(inventory);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);

        Inventory editedInventory = (Inventory) collectionsController.editInventory(inventoryDto, token).getBody();

        assertThat(editedInventory).isEqualTo(inventory);
    }


    @Test
    public void createWishlist_positive() throws UserNotFoundException, JsonProcessingException {
        when(wishListService.createWishlist(wishlistDto)).thenReturn(wishlist);
        when(cardAmountService.createCardAmount(cards)).thenReturn(cards);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);

        Wishlist createdWishlist = (Wishlist) collectionsController.createWishlist(wishlistDto, token).getBody();

        assertThat(createdWishlist).isEqualTo(wishlist);
    }

    @Test
    public void createDeck_positive() throws UserNotFoundException, JsonProcessingException {
        when(deckService.createDeck(deckDto)).thenReturn(deck);
        when(banListService.findBanList(deck.getBanList().getType())).thenReturn(banList);
        when(jwtService.parseJwt(jwt)).thenReturn(userDto);
        Deck createdDeck = (Deck) collectionsController.createDeck(deckDto, token).getBody();

        assertThat(createdDeck).isEqualTo(deck);
    }
}
