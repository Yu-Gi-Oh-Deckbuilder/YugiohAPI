package com.revature.YuGiOhDeckBuilder.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private int id;

    @Column(unique = true, nullable = false)
    @Getter @Setter private String username;

    @Column(nullable = false, length = 50)
    @Getter @Setter private String password;

    @Column
    @Getter @Setter private String firstName;

    @Column
    @Getter @Setter private String lastName;

    @Column(unique = true, nullable = false)
    @Getter @Setter private String email;

    @Column(nullable = false)
    @Getter @Setter private String userRole;
}
