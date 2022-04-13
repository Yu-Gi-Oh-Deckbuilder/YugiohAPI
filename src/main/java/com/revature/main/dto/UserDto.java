package com.revature.main.dto;

import com.revature.main.model.Role;
import lombok.*;

import javax.persistence.Column;

@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;
}
