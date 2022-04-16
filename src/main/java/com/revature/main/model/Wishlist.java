package com.revature.main.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
public class Wishlist extends Collection {

    @Column
    private String name;
    @ManyToMany
    private List<User> sharedUsers;

}
