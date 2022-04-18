package com.revature.main.service;

import com.revature.main.dao.InventoryRepository;
import com.revature.main.dao.UserRepository;
import com.revature.main.dto.InventoryDto;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.CardAmount;
import com.revature.main.model.Inventory;
import com.revature.main.model.Role;
import com.revature.main.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    InventoryRepository inventoryRepository;



    @Mock
    UserRepository userRepository;


    @InjectMocks
    InventoryService inventoryService;



    private static User user;
    private static Inventory inventory;
    private static Inventory inventory2;
    private static InventoryDto inventoryDto;


    @BeforeAll
    public static void init (){
        Role role = new Role(1, "user");
        user = new User(1,"test", "testpass", "test", "test", "test@email.com", role);
        User user2 = new User(2, "test2", "testpass", "test", "test", "test@email.com", role);
        inventory = new Inventory();
        inventory2 = new Inventory();
        inventoryDto = new InventoryDto();

        List<CardAmount> cards = new ArrayList<>();
        cards.add(new CardAmount());


        inventory.setId(1);
        inventory.setOwner(user);
        inventory.setCards(cards);

        inventory2.setId(2);
        inventory2.setOwner(user2);
        inventory2.setCards(cards);

        inventoryDto.setId(inventory.getId());
        inventoryDto.setOwner(inventory.getOwner());
        inventoryDto.setCards(inventory.getCards());
    }

    @Test
    public void getAllInventories_positive() {
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inventory);
        inventories.add(inventory2);

        when(inventoryRepository.findAll()).thenReturn(inventories);
        List<Inventory> actual = inventoryService.getAllCardsInInventory();

        assertThat(actual).isEqualTo(inventories);
    }


    @Test
    public void getAllInventoryByUserId_positive() throws UserNotFoundException {

        when(inventoryRepository.findAllCardsOwnedById(1)).thenReturn(inventory);
        when(userRepository.existsById(1)).thenReturn(true);
        Inventory actual = inventoryService.getAllCardsInInventoryByUserId(1);

        assertThat(actual).isEqualTo(inventory);
    }

    @Test
    public void getAllWishlistByUserId_UserNotExist() {
        Assertions.assertThrows(UserNotFoundException.class,
                () -> inventoryService.getAllCardsInInventoryByUserId(1));
    }


    @Test
    public void getInventoryById_positive() throws UserNotFoundException, CollectionDoesNotExistException {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.existsById(1)).thenReturn(true);
        when(userRepository.existsById(1)).thenReturn(true);
        Inventory actual = inventoryService.getInventoryById(1,user.getId());

        assertThat(actual).isEqualTo(inventory);
    }



    @Test
    public void getInventoryById_UserNotFoundException(){
        Assertions.assertThrows(UserNotFoundException.class,
                () -> inventoryService.getInventoryById(1,user.getId()));
    }

    @Test
    public void getInventoryById_CollectionDoesNotExistException(){
        when(userRepository.existsById(1)).thenReturn(true);

        Assertions.assertThrows(CollectionDoesNotExistException.class,
                () -> inventoryService.getInventoryById(1,user.getId()));
    }


    @Test
    public void editDeck_positive() throws UserNotFoundException, CollectionDoesNotExistException {

        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.existsById(inventory.getId())).thenReturn(true);
        when(userRepository.existsById(inventory.getOwner().getId())).thenReturn(true);
        Inventory expected = new Inventory();
        expected.setCards(inventory.getCards());
        expected.setOwner(inventory.getOwner());
        expected.setId(inventory.getId());

        when(inventoryRepository.saveAndFlush(expected)).thenReturn(expected);
        Inventory actual = inventoryService.editInventory(inventoryDto);

        assertThat(actual).isEqualTo(expected);
    }

    @Test public void editInventory_CollectionDoesNotExistException(){
        Assertions.assertThrows(CollectionDoesNotExistException.class,
                ()-> inventoryService.editInventory(inventoryDto));
    }

    @Test public void editInventory_UserNotFoundException(){
        when(inventoryRepository.existsById(inventory.getId())).thenReturn(true);

        Assertions.assertThrows(UserNotFoundException.class,
                ()-> inventoryService.editInventory(inventoryDto));
    }

    @Test
    public void deleteInventory_positive(){
        when(inventoryRepository.existsById(1)).thenReturn(true);
        boolean actual = inventoryService.deleteInventoryById(1);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void deleteInventory_negative(){
        boolean actual = inventoryService.deleteInventoryById(1);
        assertThat(actual).isEqualTo(false);
    }

    @Test
    public void createInventory_positive() throws UserNotFoundException {
        when(userRepository.existsById(inventory.getOwner().getId())).thenReturn(true);

        Inventory actual = inventoryService.createInventory(inventory);
        assertThat(actual).isEqualTo(inventory);
    }

    @Test
    public void createInventory_UserNotFoundException(){
        Assertions.assertThrows(UserNotFoundException.class,
                ()-> inventoryService.createInventory(inventory));
    }
}