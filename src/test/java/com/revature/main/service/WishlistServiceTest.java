package com.revature.main.service;


import com.revature.main.dao.UserRepository;
import com.revature.main.dao.WishlistRepository;
import com.revature.main.exceptions.UserNotFoundException;
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

    public static User user;
    public static  Role role;

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
    }

    @Test
    public void getAllWishlistByUserId_positive() throws UserNotFoundException {
     List<Wishlist> allWishlist = new ArrayList<>();

     HashMap<Integer, Integer> cards = new HashMap<>();
     cards.put(1,1);
     cards.put(2,2);

     Wishlist wishlist = new Wishlist();
     wishlist.setId(1);
     wishlist.setCards(cards);
     wishlist.setOwner(user);

     allWishlist.add(wishlist);

     when(wishlistRepository.findAllByUserId(1)).thenReturn(allWishlist);

     System.out.println(allWishlist);

     List<Wishlist> actual = wishlistService.getAllWishlistByUserId(1);

     assertThat(actual).isNotNull();
    }

    @Test
    public void getAllWishlistByUserId_UserNotExist() {
        when(userRepository.getById(1)).thenReturn(new User());

        Assertions.assertThrows(UserNotFoundException.class,  () ->{
           wishlistService.getAllWishlistByUserId(1);
        });
    }
}