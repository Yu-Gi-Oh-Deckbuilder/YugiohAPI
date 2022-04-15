package com.revature.main.controller;

import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.Collection;
import com.revature.main.model.Deck;
import com.revature.main.model.Inventory;
import com.revature.main.model.Wishlist;
import com.revature.main.service.DeckService;
import com.revature.main.service.WishlistService;
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
    @Getter
    WishlistService wishlistService;

    @Autowired
    @Getter
    DeckService deckService;
/*
    @Autowired
    @Getter
    InventoryService inventoryService;*/

    @GetMapping
    public ResponseEntity<?> getAllCollectionsByUserId(@RequestParam("id") int id) {
        try{
            List<Wishlist> wishLists = wishlistService.getAllWishlistByUserId(id);
            //List<Deck> deckList = deckService.getAllDecksByUserId(id);
            //Inventory inventory = inventoryService.getInventoryByUserId(id);

            List<Collection> collectionList = new ArrayList<>();

            for (Wishlist wishlist: wishLists) {
                collectionList.add(wishlist);
            }

            /*for (Deck deck: deckList) {
                collectionList.add(deck);
            }

            collectionList.add(inventory);*/

            return  ResponseEntity.ok().body(collectionList);

        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/wishlists")
    public ResponseEntity<?> getAllWishlistsByUserId(@RequestParam("id") int id) {
        try{
            List<Wishlist> wishLists = wishlistService.getAllWishlistByUserId(id);
            return  ResponseEntity.ok().body(wishLists);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/decks")
    public ResponseEntity<?> getAllDecksByUserId(@RequestParam("id") int id) {
        try{
            List<Deck> decks = deckService.getAllDecksByUserId(id);
            return  ResponseEntity.ok().body(decks);
        }catch(UserNotFoundException | CollectionDoesNotExistException | UnAuthorizedException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /*
    @GetMapping("/inventory")
    public ResponseEntity<?> getInventoryByUserId(@RequestParam("id") int id) {
        try{
            Inventory inventory = inventoryService.getInventoryByUserId(id);
            return  ResponseEntity.ok().body(inventory);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }*/

    @GetMapping("/wishlists/{wishlistId}")
    public ResponseEntity<?> getWishlistById(@RequestParam("id") int userId, @PathParam("wishlistId") int wishlistId) throws UnAuthorizedException {
        try{
            Wishlist wishList = wishlistService.getWishListById(wishlistId,userId);
            return  ResponseEntity.ok().body(wishList);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }catch (CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/decks/{deckId}")
    public ResponseEntity<?> getDeckById(@RequestParam("id") int userId,@PathParam("deckId")int deckId) {
        try{
            Deck deck = deckService.getDeckById(deckId,userId);
            return  ResponseEntity.ok().body(deck);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }catch (CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    /*@GetMapping("/inventory/{inventoryId}")
    public ResponseEntity<?> getInventoryByUserId(@RequestParam("id") int userId, @PathParam("inventoryId") int inventoryId) {
        try{
            Inventory inventory = inventoryService.getInventoryById(inventoryId,userId);
            return  ResponseEntity.ok().body(inventory);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }catch (CollectionDeosNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }*/

    @PostMapping("/wishlists")
    public ResponseEntity<?> createWishlist(Wishlist wishlist){
        try{
            Wishlist createWishlist = wishlistService.createWishlist(wishlist);
            return ResponseEntity.ok().body(createWishlist);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

   @PostMapping("/decks")
    public ResponseEntity<?> createDeck(Deck deck){
        try{
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
    public ResponseEntity<?> deleteWishlistById(@RequestParam("wishlistId")int wishlistId){
        if (wishlistService.deleteWishlistById(wishlistId)) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.status(400).body(false);
        }
    }

    @DeleteMapping("/decks/{deckId}")
    public ResponseEntity<?> deleteDeck(@RequestParam("deckId") int deckId){
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
    public ResponseEntity<?> editWishlistById(@RequestBody Wishlist wishlist){
        try{
            Wishlist editedWishlist = wishlistService.editWishlist(wishlist);
            return ResponseEntity.ok().body(editedWishlist);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }catch(CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

   @PatchMapping("/decks")
    public ResponseEntity<?> editDeck(Deck deck){
        try{
            Deck editedDeck = deckService.editDeck(deck);
            return ResponseEntity.ok().body(editedDeck);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }catch(CollectionDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
 /*
    @PatchMapping("/inventory")
    public ResponseEntity<?> editInventory(Inventory inventory){
        try{
            Inventory editedInventory = inventoryService.editInventory(inventory);
            return ResponseEntity.ok().body(editedInventory);
        }catch(UserNotFoundException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }catch(DeckDoesNotExistException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }*/
}
