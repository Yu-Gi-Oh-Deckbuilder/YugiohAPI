package com.revature.main.service;

import com.revature.main.dao.UserRepository;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.Role;
import com.revature.main.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    private static User user;
    private static  Role role;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthenticationService authenticationService;


    @BeforeAll
    public static void init (){
        role = new Role(1,"user");
        user = new User(1,"test", "testpass", "test", "test", "test@email.com", role);
    }

    @Test
    public void login_positive() throws UserNotFoundException {
        when(userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword())).thenReturn(user);

        User actual = authenticationService.login(user.getUsername(),user.getPassword());

        assertThat(actual).isEqualTo(user);
    }

    @Test
    public void login_IllegalArgumentException(){
        Assertions.assertThrows(IllegalArgumentException.class,()->{
           authenticationService.login("","");
        });
    }

    @Test
    public void login_UserNotFoundException(){
        when(userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword())).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class,()->{
           authenticationService.login(user.getUsername(),user.getPassword());
        });
    }
}
