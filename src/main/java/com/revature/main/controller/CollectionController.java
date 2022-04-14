package com.revature.main.controller;

import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.Collection;
import com.revature.main.model.Deck;
import com.revature.main.model.Inventory;
import com.revature.main.model.Wishlist;
import com.revature.main.service.WishlistService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /*@Autowired
    @Getter
    DeckService deckService;

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

}
