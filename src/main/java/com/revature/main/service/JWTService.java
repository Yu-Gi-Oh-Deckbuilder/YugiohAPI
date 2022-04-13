package com.revature.main.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.main.dto.UserDto;
import com.revature.main.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;

@Service
public class JWTService {
    private Key key;

    public JWTService() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS384);

    }

    public String createJwt(User user) throws JsonProcessingException {
        UserDto dto = new UserDto(user.getId(), user.getFirstName(), user.getLastName(),user.getEmail(),
                user.getUserRole());

        String jwt = Jwts.builder()
                .claim("user_dto", new ObjectMapper().writeValueAsString(dto))
                .signWith(key)
                .compact();

        return jwt;
    }

    public UserDto parseJwt(String jwt) throws JsonProcessingException {
        Jws<Claims> token = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

        String dtoString = (String) token.getBody().get("user_dto");

        return (new ObjectMapper()).readValue(dtoString, UserDto.class);
    }
}
