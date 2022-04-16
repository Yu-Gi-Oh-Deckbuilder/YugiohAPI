package com.revature.main.service;


import com.revature.main.dao.InventoryRepository;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService extends EntityService{




    @Autowired
    private InventoryRepository inventoryRepository;


    public List<Inventory> getAllCardsInInventory(){
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories;
    }



    public List<Inventory> getAllCardsInIneventoryById(int id) throws UserNotFoundException{


        checkIfUserExists(id);

        List<Inventory> inventories = inventoryRepository.findAllCardsOwnedById(id);
        return inventories;
    }

    @Transactional
    public Inventory editinventory(Inventory inventory) throws UserNotFoundException, CollectionDoesNotExistException{

        if(!inventoryRepository.existsById((inventory.getId()))){
            throw new CollectionDoesNotExistException("Inventory with id "+inventory.getId()+" does not exist");
        }

        if(!userRepository.existsById(inventory.getOwner().getId())){
            throw new UserNotFoundException("User with id "+inventory.getOwner().getId()+ " does not exist");
        }

        Inventory source = inventoryRepository.findById(inventory.getId()).get();
        source.setCards(inventory.getCards());
        return inventoryRepository.saveAndFlush(source);
    }


    @Transactional
    public Inventory createInventory(Inventory inventory) throws UserNotFoundException {
        checkIfUserExists(inventory.getOwner().getId());

        inventoryRepository.saveAndFlush(inventory);

        return inventory;
    }



    //TODO: delete by id
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

        Inventory inventory = inventoryRepository.findById(id).get();

        return inventory;
    }
}
