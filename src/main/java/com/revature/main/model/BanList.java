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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String banList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BanList banList1 = (BanList) o;
        return id == banList1.id && Objects.equals(banList, banList1.banList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, banList);
    }
}