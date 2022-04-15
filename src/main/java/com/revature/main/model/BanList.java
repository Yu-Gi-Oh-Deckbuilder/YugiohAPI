package com.revature.main.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ban_list")
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Setter
public class BanList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BanList type1 = (BanList) o;
        return id == type1.id && Objects.equals(type, type1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}