package com.revature.user.service;

import com.revature.user.model.User;
import com.revature.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
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
        User expected = new User(1, "test", "password", "test", "test", "test@test.com", "user");
        when(userRepository.findByUsername("test")).thenReturn(expected);
        User actual = userService.getUserByUsername("test");
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void getUserByIdTest_positive() {
        User expected = new User(1, "test", "password", "test", "test", "test@test.com", "user");
        when(userRepository.getById(1)).thenReturn(expected);
        User actual = userService.getUserById(1);
        assertThat(actual).isEqualTo(expected);
    }
}
