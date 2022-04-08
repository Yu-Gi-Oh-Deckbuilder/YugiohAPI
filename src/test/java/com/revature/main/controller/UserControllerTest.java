package com.revature.main.controller;

import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.Role;
import com.revature.main.model.User;
import com.revature.main.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
    public void getAllUsersTest_positive() throws UserNotFoundException {
        List<User> users = new ArrayList<>();

        Role role = new Role(1, "user");
        User user1 = new User(1, "test", "password", "test", "test", "test@test.com", role);
        User user2 = new User(2, "test", "password", "test", "test", "test@test.com", role);
        User user3 = new User(3, "test", "password", "test", "test", "test@test.com", role);
        User user4 = new User(4, "test", "password", "test", "test", "test@test.com", role);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity responseEntity = userController.getAllUsers();
        List<User> userList = (List<User>) responseEntity.getBody();
        assertThat(userList).isEqualTo(users);
    }

    @Test
    public void getUserByUsernameTest_positive() {
        Role role = new Role(1, "user");
        User expected = new User(1, "test", "password", "test", "test", "test@test.com", role);
        when(userService.getUserByUsername("test")).thenReturn(expected);

        ResponseEntity responseEntity =  userController.getUserByUsername("test");
        User user = (User) responseEntity.getBody();
        assertThat(user).isEqualTo(expected);
    }
    @Test
    public void getUserByIdTest_positive() throws UserNotFoundException {
        Role role = new Role(1, "user");
        User user = new User(1, "test", "password", "test", "test", "test@test.com", role);
        when(userService.getUserById(1)).thenReturn(user);

        ResponseEntity responseEntity =  userController.getUserById(1);
        User expected = (User) responseEntity.getBody();

        assertThat(user).isEqualTo(expected);
    }

    @Test
    public void deleteUserById_positive() throws UserNotFoundException {
        when(userService.deleteUserById(1)).thenReturn(true);
        boolean actual = userController.deleteUserById(1);
        assertThat(actual).isEqualTo(true);


    }

    @Test
    public void deleteUserById_negative(){
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            userController.deleteUserById(1);
        });
    }
}