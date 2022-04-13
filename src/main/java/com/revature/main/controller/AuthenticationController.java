package com.revature.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.main.dto.LoginDto;
import com.revature.main.exceptions.UserNotFoundException;
import com.revature.main.model.User;
import com.revature.main.service.AuthenticationService;
import com.revature.main.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Profile("prod")
@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) throws JsonProcessingException {
        try {
            User user = authService.login(dto.getUsername(), dto.getPassword());

            String jwt = jwtService.createJwt(user);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("token", jwt);

            return ResponseEntity.ok().headers(responseHeaders).body(user);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
