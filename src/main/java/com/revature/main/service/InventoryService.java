package com.revature.main.service;


import com.revature.main.dao.InventoryRepository;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.Inventory;
import com.revature.main.util.CollectionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService extends EntityService{




    @Autowired
    private InventoryRepository inventoryRepository;


    public List<Inventory> getAllCardsInInventory(){
        return inventoryRepository.findAll();
    }

    public Inventory getAllCardsInInventoryByUserId(int id) throws UserNotFoundException{
        checkIfUserExists(id);

        return inventoryRepository.findAllCardsOwnedById(id);
    }

    @Transactional
    public Inventory editInventory(Inventory inventory) throws UserNotFoundException, CollectionDoesNotExistException{

        if(!inventoryRepository.existsById((inventory.getId()))){
            throw new CollectionDoesNotExistException("Inventory with id "+inventory.getId()+" does not exist");
        }

        if(!userRepository.existsById(inventory.getOwner().getId())){
            throw new UserNotFoundException("User with id "+inventory.getOwner().getId()+ " does not exist");
        }

        Inventory source = inventoryRepository.findById(inventory.getId()).get();
        source.setCards(inventory.getCards());
        source.setTotalCards(CollectionUtility.calculateTotal(inventory.getCards()));
        return inventoryRepository.saveAndFlush(source);
    }


    @Transactional
    public Inventory createInventory(Inventory inventory) throws UserNotFoundException {
        checkIfUserExists(inventory.getOwner().getId());

        inventory.setTotalCards(CollectionUtility.calculateTotal(inventory.getCards()));
        inventoryRepository.saveAndFlush(inventory);

        return inventory;
    }

    @Transactional
    public boolean deleteInventoryById(int id){
        if ( !inventoryRepository.existsById(id)){
            return false;
        }
        inventoryRepository.deleteById(id);
        return true;
    }


    public Inventory getInventoryById(int id, int userId) throws UserNotFoundException, CollectionDoesNotExistException {
        checkIfUserExists(userId);

        if(!inventoryRepository.existsById(id)){
            throw new CollectionDoesNotExistException("Deck does not exist");
        }

        return inventoryRepository.findById(id).get();
    }
}
