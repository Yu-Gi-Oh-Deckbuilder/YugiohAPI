package com.revature.main.service;

import com.revature.main.dao.UserRepository;
import com.revature.main.dao.WishlistRepository;
import com.revature.main.model.Role;
import com.revature.main.model.User;
import com.revature.main.model.Wishlist;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    private static User user;
    private static  Role role;

    @BeforeAll
    public static void init () {
        role = new Role(1, "user");
        user = new User(1, "test", "testpass", "test", "test", "test@email.com", role);
    }
    @Test

    public void createJwt_positive(){

    }
}
