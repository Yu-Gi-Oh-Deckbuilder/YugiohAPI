package com.revature.main.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="card_amount")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CardAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private int cardId;

    @Column
    private int cardAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardAmount that = (CardAmount) o;
        return Id == that.Id && cardId == that.cardId && cardAmount == that.cardAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, cardId, cardAmount);
    }
}
