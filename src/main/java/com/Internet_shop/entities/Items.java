package com.Internet_shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "stars")
    private int stars;

    @Column(name = "smallPicURL")
    private String smallPicURL;

    @Column(name = "largePicURL")
    private String largePicURL;

    @Column(name="addedDate", columnDefinition="timestamp NOT NULL DEFAULT NOW()", insertable = false, updatable = false)
    private Timestamp addedDate;

    @Column(name = "inTopPage")
    private boolean inTopPage;


    @ManyToOne(fetch = FetchType.EAGER)
    private Brands brand;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Categories> categories;
}
