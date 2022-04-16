package com.revature.main.controller;

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

    /*@GetMapping
    public ResponseEntity<?> getAllWishlists(@RequestParam("id") int id) {
        try{
            List<Wishlist> wishLists = wishlistService.getAllWishlistByUserId(id);
            //List<Deck> deckList = deckService.getAllDecksByUserId(id);
            //Inventory inventory = inventoryService.getInventoryByUserId(id);

            //List<Wishlist> collectionList = new ArrayList<>();



            *//*for (Deck deck: deckList) {
                collectionList.add(deck);
            }

            collectionList.add(inventory);*//*

            return  ResponseEntity.ok().body(wishLists);

        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
*/

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
    public ResponseEntity<?> getAllWishlistsByUserId(@PathVariable("userId") int userId) {
        try{
            List<Wishlist> wishLists = wishlistService.getAllWishlistByUserId(userId);
            return  ResponseEntity.ok().body(wishLists);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/users/{userId}/decks")
    public ResponseEntity<?> getAllDecksByUserId(@PathVariable("userId") int userId) {
        try{
            List<Deck> decks = deckService.getAllDecksByUserId(userId);
            return  ResponseEntity.ok().body(decks);
        }catch(UserNotFoundException | CollectionDoesNotExistException | UnAuthorizedException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @GetMapping("/users/{userId}/inventory")
    public ResponseEntity<?> getInventoryByUserId(@PathVariable("userId") int id) {
        try{
            Inventory inventory = inventoryService.getAllCardsInInventoryByUserId(id);
            return  ResponseEntity.ok().body(inventory);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("users/{userId}/wishlists/{wishlistId}")
    public ResponseEntity<?> getWishlistById(@PathVariable("userId") int userId, @PathVariable("wishlistId") int wishlistId) throws UnAuthorizedException {
        try{
            Wishlist wishList = wishlistService.getWishListById(wishlistId,userId);
            return  ResponseEntity.ok().body(wishList);
        }catch(UserNotFoundException | CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/users/{userId}/decks/{deckId}")
    public ResponseEntity<?> getDeckById(@PathVariable("userId") int userId,@PathVariable("deckId")int deckId) {
        try{
            Deck deck = deckService.getDeckById(deckId,userId);
            return  ResponseEntity.ok().body(deck);
        }catch(UserNotFoundException | CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


//    @GetMapping("/inventory/{inventoryId}")
//    public ResponseEntity<?> getInventoryByUserId(@RequestParam("id") int userId, @PathParam("inventoryId") int inventoryId) {
//        try{
//            Inventory inventory = inventoryService.getInventoryById(inventoryId,userId);
//            return  ResponseEntity.ok().body(inventory);
//        }catch(UserNotFoundException e){
//            return ResponseEntity.status(400).body(e.getMessage());
//        }catch (CollectionDoesNotExistException e){
//            return ResponseEntity.status(400).body(e.getMessage());
//        }
//    }

    @PostMapping("/wishlists")
    public ResponseEntity<?> createWishlist(@RequestBody Wishlist wishlist){
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
    public ResponseEntity<?> createDeck(@RequestBody Deck deck){
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
 /*
    @PostMapping("/wishlists")
    public ResponseEntity<?> createInventory(Inventory inventory){
        try{
            Inventory createdInventory = inventoryService.createWishlist(inventory);
            return ResponseEntity.ok().body(createdInventory);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }*/

    @DeleteMapping("/wishlists/{wishlistId}")
    public ResponseEntity<?> deleteWishlistById(@PathVariable("wishlistId")int wishlistId){
        if (wishlistService.deleteWishlistById(wishlistId)) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.status(400).body(false);
        }
    }

    @DeleteMapping("/decks/{deckId}")
    public ResponseEntity<?> deleteDeck(@PathVariable("deckId") int deckId){
        if (deckService.deleteDeckById(deckId)) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.status(400).body(false);
        }
    }
    /*
        @DeleteMapping("/inventory/{inventoryId}")
        public ResponseEntity<?> deleteInventory(@RequestParam("inventoryId") int inventoryId){
            if (inventoryService.deleteDeckById(inventoryId)) {
                return ResponseEntity.ok().body(true);
            } else {
                return ResponseEntity.status(400).body(false);
            }
        }*/
    @PutMapping("/wishlists")
    public ResponseEntity<?> editWishlistById(@RequestBody Wishlist wishlist) {
        try{
            Wishlist editedWishlist = wishlistService.editWishlist(wishlist);
            return ResponseEntity.ok().body(editedWishlist);
        }catch(UserNotFoundException | CollectionDoesNotExistException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

   @PatchMapping("/decks")
    public ResponseEntity<?> editDeck(@RequestBody Deck deck){
        try{
            Deck editedDeck = deckService.editDeck(deck);
            return ResponseEntity.ok().body(editedDeck);
        }catch(UserNotFoundException | CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("/inventory")
    public ResponseEntity<?> editInventory(@RequestBody Inventory inventory){
        try{
            Inventory editedInventory = inventoryService.editInventory(inventory);
            return ResponseEntity.ok().body(editedInventory);
        }catch(UserNotFoundException | CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
