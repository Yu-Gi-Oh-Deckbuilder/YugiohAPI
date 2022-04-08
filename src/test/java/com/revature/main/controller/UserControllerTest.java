package com.revature.main.controller;

import com.revature.main.model.User;
import com.revature.main.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void contextLoads() {
        assertThat(userController).isNotNull();
    }

    @Test
    public void getAllUsersTest_positive() {
        List<User> userList = userController.getAllUsers();
        assertThat(userList).isNotNull();
    }

    @Test
    public void getUserByUsernameTest_positive() {
        User expected = new User(1, "test", "password", "test", "test", "test@test.com", "user");
        when(userService.getUserByUsername("test")).thenReturn(expected);
        User user = userController.getUserByUsername("test");
        assertThat(user).isEqualTo(expected);
    }
    @Test
    public void getUserByIdTest_positive() {
        User user = new User(1, "test", "password", "test", "test", "test@test.com", "user");
        when(userService.getUserById(1)).thenReturn(user);
        User actual = userController.getUserById(1);
        assertThat(actual).isEqualTo(user);
    }
}