package com.Internet_shop.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Sale")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name="addedDate", columnDefinition="timestamp NOT NULL DEFAULT NOW()", insertable = false, updatable = false)
    private Timestamp addedDate;

    @OneToOne(fetch = FetchType.EAGER)
    Items items;

    @ManyToMany(fetch = FetchType.LAZY)
    List<Users> users;
}
