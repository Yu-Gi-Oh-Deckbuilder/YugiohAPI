package com.revature.main.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "wishlist")
public class Wishlist extends Collection {

    @ManyToMany
    private List<User> sharedUsers;

}