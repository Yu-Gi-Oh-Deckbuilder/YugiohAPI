package com.revature.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.main.dto.UserDto;
import com.revature.main.exceptions.CardAmountDoesNotExistException;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.*;
import com.revature.main.service.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Profile("prod")
@RestController
@RequestMapping("/collections")
@CrossOrigin
public class CollectionController {

    private static final String UNAUTHORIZED = "Unauthorized access. User Id does not match";
    @Autowired
    WishlistService wishlistService;

    @Autowired
    DeckService deckService;

    @Autowired
    CardAmountService cardAmountService;

    @Autowired
    BanListService banListService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    JWTService jwtService;

    @GetMapping("/wishlists")
    public ResponseEntity<?> getAllWishlists() {
        List<Wishlist> wishLists = wishlistService.getAllWishlists();
        return  ResponseEntity.ok().body(wishLists);
    }

    @GetMapping("/decks")
    public ResponseEntity<?> getAllDecks() {
        List<Deck> decks = deckService.getAllDecks();
        return  ResponseEntity.ok().body(decks);
    }

    @GetMapping("/inventories")
    public ResponseEntity<?> getAllInventories() {
        List<Inventory> inventories = inventoryService.getAllCardsInInventory();
        return  ResponseEntity.ok().body(inventories);
    }

    @GetMapping("/users/{userId}/wishlists")
    public ResponseEntity<?> getAllWishlistsByUserId(
            @PathVariable("userId") int userId, @RequestHeader("Authorization") String token) throws JsonProcessingException {
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            List<Wishlist> wishLists = wishlistService.getAllWishlistByUserId(userId);
            return  ResponseEntity.ok().body(wishLists);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/users/{userId}/decks")
    public ResponseEntity<?> getAllDecksByUserId(
            @PathVariable("userId") int userId, @RequestHeader("Authorization") String token) throws JsonProcessingException {
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            List<Deck> decks = deckService.getAllDecksByUserId(userId);
            return  ResponseEntity.ok().body(decks);
        }catch(UserNotFoundException | CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @GetMapping("/users/{userId}/inventory")
    public ResponseEntity<?> getInventoryByUserId(
            @PathVariable("userId") int userId, @RequestHeader("Authorization") String token) throws JsonProcessingException {
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            Inventory inventory = inventoryService.getAllCardsInInventoryByUserId(userId);
            return  ResponseEntity.ok().body(inventory);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("users/{userId}/wishlists/{wishlistId}")
    public ResponseEntity<?> getWishlistById(
            @PathVariable("userId") int userId,
            @PathVariable("wishlistId") int wishlistId,
            @RequestHeader("Authorization") String token) throws UnAuthorizedException, JsonProcessingException {
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            Wishlist wishList = wishlistService.getWishListById(wishlistId,userId);
            return  ResponseEntity.ok().body(wishList);
        }catch(UserNotFoundException | CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/users/{userId}/decks/{deckId}")
    public ResponseEntity<?> getDeckById(
            @PathVariable("userId") int userId,
            @PathVariable("deckId")int deckId,
            @RequestHeader("Authorization") String token) throws JsonProcessingException {
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            Deck deck = deckService.getDeckById(deckId,userId);
            return  ResponseEntity.ok().body(deck);
        }catch(UserNotFoundException | CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @PostMapping("/wishlists")
    public ResponseEntity<?> createWishlist(@RequestBody Wishlist wishlist, @RequestHeader("Authorization") String token) throws JsonProcessingException {
        int userId = wishlist.getOwner().getId();
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            List<CardAmount> cardAmount = cardAmountService.createCardAmount(wishlist.getCards());
            wishlist.setCards(cardAmount);
            Wishlist createWishlist = wishlistService.createWishlist(wishlist);
            return ResponseEntity.ok().body(createWishlist);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

   @PostMapping("/decks")
    public ResponseEntity<?> createDeck(@RequestBody Deck deck, @RequestHeader("Authorization") String token) throws JsonProcessingException {
       int userId = deck.getOwner().getId();
       if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            BanList deckBanList = banListService.findBanList(deck.getBanList().getType());
            deck.setBanList(deckBanList);
            List<CardAmount> cardAmount = cardAmountService.createCardAmount(deck.getCards());
            deck.setCards(cardAmount);
            Deck createDeck = deckService.createDeck(deck);
            return ResponseEntity.ok().body(createDeck);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{userId}/wishlists/{wishlistId}")
    public ResponseEntity<?> deleteWishlistById(
            @PathVariable("userId") int userId,
            @PathVariable("wishlistId")int wishlistId,
            @RequestHeader("Authorization") String token) throws JsonProcessingException {
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        if (wishlistService.deleteWishlistById(wishlistId)) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.status(400).body(false);
        }
    }

    @DeleteMapping("/users/{userId}/decks/{deckId}")
    public ResponseEntity<?> deleteDeck(
            @PathVariable("userId") int userId,
            @PathVariable("deckId") int deckId,
            @RequestHeader("Authorization") String token) throws JsonProcessingException {
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        if (deckService.deleteDeckById(deckId)) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.status(400).body(false);
        }
    }

    @PatchMapping("/wishlists")
    public ResponseEntity<?> editWishlistById(@RequestBody Wishlist wishlist, @RequestHeader("Authorization") String token) throws JsonProcessingException {
        int userId = wishlist.getOwner().getId();
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            Wishlist editedWishlist = wishlistService.editWishlist(wishlist);
            return ResponseEntity.ok().body(editedWishlist);
        }catch(UserNotFoundException | CollectionDoesNotExistException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

   @PatchMapping("/decks")
    public ResponseEntity<?> editDeck(@RequestBody Deck deck, @RequestHeader("Authorization") String token) throws JsonProcessingException {
       int userId = deck.getOwner().getId();
       if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            Deck editedDeck = deckService.editDeck(deck);
            return ResponseEntity.ok().body(editedDeck);
        }catch(UserNotFoundException | CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("/inventory")
    public ResponseEntity<?> editInventory(@RequestBody Inventory inventory, @RequestHeader("Authorization") String token) throws JsonProcessingException {
        int userId = inventory.getOwner().getId();
        if(!returnFalseIfUserIdMismatch(userId, token)) ResponseEntity.status(401).body(UNAUTHORIZED);
        try{
            Inventory editedInventory = inventoryService.editInventory(inventory);
            return ResponseEntity.ok().body(editedInventory);
        }catch(UserNotFoundException | CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    private boolean returnFalseIfUserIdMismatch(int userId, String token) throws JsonProcessingException {
        String jwt = token.split(" ")[1];
        UserDto user = jwtService.parseJwt(jwt);
        return user.getId() == userId;
    }
}
