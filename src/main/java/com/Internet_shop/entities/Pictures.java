package com.Internet_shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Pictures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pictures {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    String url;

    @Column(name="addedDate", columnDefinition="timestamp NOT NULL DEFAULT NOW()", insertable = false, updatable = false)
    private Timestamp addedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    Items items;
}
