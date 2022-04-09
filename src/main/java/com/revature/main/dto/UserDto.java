package com.revature.main.dto;

import lombok.*;

import javax.persistence.Column;

@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

}
