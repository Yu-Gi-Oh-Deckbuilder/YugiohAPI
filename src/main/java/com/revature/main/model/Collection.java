package com.revature.main.model;

import com.revature.main.dao.CardAmountRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Data
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<CardAmount> cards;

    @Column
    private int totalCards;

    @ManyToOne
    private User owner;
}
