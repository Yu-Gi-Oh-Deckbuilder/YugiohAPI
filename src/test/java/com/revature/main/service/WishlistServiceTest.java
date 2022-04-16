package com.revature.main.service;


import com.revature.main.dao.UserRepository;
import com.revature.main.dao.WishlistRepository;
import com.revature.main.exceptions.UnAuthorizedException;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.exceptions.CollectionDoesNotExistException;
import com.revature.main.model.CardAmount;
import com.revature.main.model.Role;
import com.revature.main.model.User;
import com.revature.main.model.Wishlist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {

    private static User user;
    private static User user2;
    private static  Role role;
    private static Wishlist wishlist;
    private static List<CardAmount> cards;

    @Mock
    WishlistRepository wishlistRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeAll
    public static void init (){
        role = new Role(1,"user");
        user = new User(1,"test", "testpass", "test", "test", "test@email.com", role);
        user2 =  new User(2,"test2", "testpass", "test", "test", "test@email.com", role);
        wishlist = new Wishlist();

        cards = new ArrayList<>();
        cards.add(new CardAmount());

        wishlist.setId(1);
        wishlist.setOwner(user);
        wishlist.setCards(cards);
        wishlist.setSharedUsers(new ArrayList<User>());
        wishlist.getSharedUsers().add(user);
        wishlist.getSharedUsers().add(user2);
    }

    @Test
    public void getAllWishlistByUserId_positive() throws UserNotFoundException {
     List<Wishlist> allWishlist = new ArrayList<>();
     allWishlist.add(wishlist);

     when(wishlistRepository.findAllByUserId(1)).thenReturn(allWishlist);
     when(userRepository.existsById(1)).thenReturn(true);
     List<Wishlist> actual = wishlistService.getAllWishlistByUserId(1);

     assertThat(actual).isNotNull();
    }

    @Test
    public void getAllWishlistByUserId_UserNotExist() {

        Assertions.assertThrows(UserNotFoundException.class,  () ->{
           wishlistService.getAllWishlistByUserId(1);
        });
    }

    @Test
    public void getWishlistById_positive() throws UserNotFoundException, UnAuthorizedException, CollectionDoesNotExistException {
        when(userRepository.getById(1)).thenReturn(user);

        when(wishlistRepository.getById(1)).thenReturn(wishlist);
        when(wishlistRepository.existsById(1)).thenReturn(true);
        when(userRepository.existsById(1)).thenReturn(true);
        Wishlist actual = wishlistService.getWishListById(1,user.getId());

        assertThat(actual).isEqualTo(wishlist);
    }

    @Test
    public void getWishlistById_SharedUser() throws UserNotFoundException, UnAuthorizedException, CollectionDoesNotExistException {
        when(userRepository.getById(2)).thenReturn(user2);

        when(wishlistRepository.getById(1)).thenReturn(wishlist);
        when(wishlistRepository.existsById(1)).thenReturn(true);
        when(userRepository.existsById(2)).thenReturn(true);
        Wishlist actual = wishlistService.getWishListById(1,user2.getId());

        assertThat(actual).isEqualTo(wishlist);
    }

    @Test
    public void getWishlistById_UserNotFoundException(){
        when(userRepository.getById(user.getId())).thenReturn(new User());

        Assertions.assertThrows(UserNotFoundException.class,  () ->{
            wishlistService.getWishListById(1,user.getId());
        });
    }

    @Test
    public void getWishlistById_UnAuthorizedException(){
        User user3 = new User(3,"test2", "testpass", "test", "test", "test@email.com", role);

        when(userRepository.getById(3)).thenReturn(user3);

        when(userRepository.existsById(3)).thenReturn(true);
        when(wishlistRepository.getById(1)).thenReturn(wishlist);
        when(wishlistRepository.existsById(1)).thenReturn(true);
        Assertions.assertThrows(UnAuthorizedException.class,()->{
           wishlistService.getWishListById(1,user3.getId());
        });
    }

    @Test
    public void getWishlistById_WishlistDoesNotExistException() {
       when(userRepository.getById(2)).thenReturn(user2);
       when(userRepository.existsById(2)).thenReturn(true);
       Assertions.assertThrows(CollectionDoesNotExistException.class,()->{
          wishlistService.getWishListById(1,user2.getId());
       });
    }

    @Test
    public void editWishlist_positive() throws UserNotFoundException, CollectionDoesNotExistException {
        when(wishlistRepository.getById(1)).thenReturn(wishlist);
        when(wishlistRepository.existsById(wishlist.getId())).thenReturn(true);
        when(userRepository.existsById(wishlist.getOwner().getId())).thenReturn(true);
        Wishlist expected = new Wishlist();
        expected.setCards(wishlist.getCards());
        expected.setSharedUsers(wishlist.getSharedUsers());
        expected.setOwner(wishlist.getOwner());
        expected.setId(wishlist.getId());

        when(wishlistRepository.saveAndFlush(expected)).thenReturn(expected);
        Wishlist actual = wishlistService.editWishlist(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test public void editWishlist_WishlistNotFoundException(){
        Assertions.assertThrows(CollectionDoesNotExistException.class,()->{
            wishlistService.editWishlist(wishlist);
        });
    }

    @Test
    public void editWishlist_UserNotFoundException(){
        when(wishlistRepository.existsById(wishlist.getId())).thenReturn(true);

        Assertions.assertThrows(UserNotFoundException.class,()->{
            wishlistService.editWishlist(wishlist);
        });
    }

    @Test
    public void deleteWishlist_positive(){
        when(wishlistRepository.existsById(1)).thenReturn(true);
        boolean actual = wishlistService.deleteWishlistById(1);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void deleteWishlist_negative(){
        boolean actual = wishlistService.deleteWishlistById(1);
        assertThat(actual).isEqualTo(false);
    }

    @Test
    public void createWishlist_positive() throws UserNotFoundException {
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);
        when(userRepository.existsById(wishlist.getOwner().getId())).thenReturn(true);
        Wishlist actual = wishlistService.createWishlist(wishlist);
        assertThat(actual).isEqualTo(wishlist);
    }

    @Test
    public void createWishlist_UserNotFoundException(){
        Assertions.assertThrows(UserNotFoundException.class, ()->{
            wishlistService.createWishlist(wishlist);
        });
    }




}