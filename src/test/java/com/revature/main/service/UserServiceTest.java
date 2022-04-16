package com.revature.main.service;

import com.revature.main.dao.UserRepository;
import com.revature.main.dto.UserDto;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.Role;
import com.revature.main.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
        when(userRepository.findById(1)).thenReturn(Optional.of(expected));
        User actual = userService.getUserById(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void deleteUserById_positive() throws UserNotFoundException {
        Role role = new Role(1, "user");
        User expected = new User(2, "test", "password", "test", "test", "test@test.com", role);
        when(userRepository.existsById(2)).thenReturn(true);
        boolean actual = userService.deleteUserById(2);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void deleteUserById_negative () throws UserNotFoundException {
        Assertions.assertThrows(UserNotFoundException.class, ()->{
            userService.deleteUserById(1);
        });
    }

    @Test
    public void updateUserAdmin_positive() throws UserNotFoundException {
        Role role = new Role(1, "user");
        User user = new User(2, "test", "password", "test", "test", "test@test.com", role);
        when(userRepository.getById(2)).thenReturn(user);
        when(userRepository.existsById(2)).thenReturn(true);
        User updateUser = new User(2,"newusername1","pass", "test", "testlast", "new@email.com",role);
        User actual = userService.updateUser(updateUser);

        assertThat(actual.getUsername()).isEqualTo("newusername1");
        assertThat(actual.getPassword()).isEqualTo("pass");
        assertThat(actual.getFirstName()).isEqualTo("test");
        assertThat(actual.getLastName()).isEqualTo("testlast");
        assertThat(actual.getEmail()).isEqualTo("new@email.com");
    }

    @Test
    public void updateUserAdmin_negative(){
        //null because we don't get to the actual dto
        Assertions.assertThrows(UserNotFoundException.class,()->{
            userService.updateUser(new User());
        });
    }
    
    @Test
    public void updateUser_positive() throws UserNotFoundException {
        Role role = new Role(1, "user");
        User user = new User(2, "test", "password", "test", "test", "test@test.com", role);
        when(userRepository.getById(2)).thenReturn(user);
        when(userRepository.existsById(2)).thenReturn(true);
        UserDto userDto = new UserDto(2, "test", "testlast", "new@email.com",role);
        User actual = userService.updateUser(userDto);

        assertThat(actual.getFirstName()).isEqualTo("test");
        assertThat(actual.getLastName()).isEqualTo("testlast");
        assertThat(actual.getEmail()).isEqualTo("new@email.com");
    }

    @Test
    public void updateUser_negative() throws UserNotFoundException {
        //null because we don't get to the actual dto
        Assertions.assertThrows(UserNotFoundException.class,()->{
            userService.updateUser(new UserDto());
        });
    }

    @Test
    public void createUser_positive(){
        Role role = new Role(1, "user");
        User user = new User(2, "test", "password", "test", "test", "test@test.com", role);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        User actual = userService.createUser(user);
        assertThat(actual).isEqualTo(user);
    }

    @Test
    public void createUser_IllegalArgumentException(){
        Role role = new Role(1, "user");
        User user = new User(2, "test", "password", "test123", "test", "test@test.com", role);

        Assertions.assertThrows(IllegalArgumentException.class,()->{
           userService.createUser(user);
        });

    }
}
