package com.revature.main.dto;

import lombok.*;

@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Setter
public class LoginDto {

    private String username;
    private String password;
}
