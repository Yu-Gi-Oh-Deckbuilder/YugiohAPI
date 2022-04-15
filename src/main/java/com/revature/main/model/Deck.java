package com.revature.main.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "deck")
@Getter
@Setter
public class Deck extends Collection{

    @ManyToOne
    private BanList banList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Deck deck = (Deck) o;
        return Objects.equals(banList, deck.banList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), banList);
    }
}