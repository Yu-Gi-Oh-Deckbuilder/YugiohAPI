package com.revature.main.service;

import com.revature.main.dao.UserRepository;
import com.revature.main.dto.UserDto;
import com.revature.main.model.Role;
import com.revature.main.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getAllUsersTest_positive() {
        List<User> userList = userService.getAllUsers();
        assertThat(userList).isNotNull();
    }

    @Test
    public void getUserByUsernameTest_positive() {
        Role role = new Role(1, "user");
        User expected = new User(1, "test", "password", "test", "test", "test@test.com", role);
        when(userRepository.findByUsername("test")).thenReturn(expected);
        User actual = userService.getUserByUsername("test");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getUserByIdTest_positive() {
        Role role = new Role(1, "user");
        User expected = new User(1, "test", "password", "test", "test", "test@test.com", role);
        when(userRepository.getById(1)).thenReturn(expected);
        User actual = userService.getUserById(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void deleteUserById_positive(){
        Role role = new Role(1, "user");
        User expected = new User(2, "test", "password", "test", "test", "test@test.com", role);
        when(userRepository.getById(2)).thenReturn(expected);
        boolean actual = userService.deleteUserById(2);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void deleteUserById_negative (){
        boolean actual = userService.deleteUserById(2);
        assertThat(actual).isEqualTo(false);
    }

    @Test
    public void updateUser_positive(){
        Role role = new Role(1, "user");
        User user = new User(2, "test", "password", "test", "test", "test@test.com", role);
        when(userRepository.getById(2)).thenReturn(user);

        UserDto userDto = new UserDto("newusername1", "pass", "test", "testlast", "new@email.com");
        User actual = userService.updateUser(2, userDto);

        assertThat(actual.getUsername()).isEqualTo("newusername1");
        assertThat(actual.getPassword()).isEqualTo("pass");
        assertThat(actual.getFirstName()).isEqualTo("test");
        assertThat(actual.getLastName()).isEqualTo("testlast");
        assertThat(actual.getEmail()).isEqualTo("new@email.com");


    }

    @Test
    public void updateUser_negative(){
        //null because we don't get to the actual dto
        User actual = userService.updateUser(1, null);
        assertThat(actual).isEqualTo(null);
    }

    @Test
    public void createUser_positive(){
        Role role = new Role(1, "user");
        User user = new User(2, "test", "password", "test", "test", "test@test.com", role);
        when(userRepository.save(user)).thenReturn(user);
        User actual = userService.createUser(user);
        assertThat(actual).isEqualTo(user);
    }




    //TODO need to work on negative test for the create user service (check why is now throwing the exception)




//    @Test void createUser_negative_usernamealreadyexist(){
//        User existentUser = new User(1, "test", "password", "test", "test", "test@test.com", "user");
//        when(userRepository.save(existentUser)).thenReturn(existentUser);
//
//        User user = new User(1, "test", "password", "test", "test", "test@test.com", "user");
//        when(userRepository.save(user)).thenReturn(user);
//        User actual = userService.createUser(user);
//
//        List<User> userList = userService.getAllUsers();
//        System.out.println("something to see my string " + userList);
//
//        assertThat(actual).isEqualTo(user);
//    }
//
//    @Test void createUser_negative_userIsNull(){
//
//        User user = null;
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            userService.createUser(user);
//        });
//
//        //IllegalArgumentException
//    }
}
