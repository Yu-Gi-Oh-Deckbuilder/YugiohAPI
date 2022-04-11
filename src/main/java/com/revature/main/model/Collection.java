package com.revature.main.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Data
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private HashMap<Integer, Integer> cards;

    @ManyToOne
    private User owner;

}
