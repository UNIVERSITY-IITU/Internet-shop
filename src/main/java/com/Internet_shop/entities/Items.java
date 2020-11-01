package com.Internet_shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

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

    @Column(name = "description")
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


    public Items(String name, String description, Double price, int stars, String smallPicURL, String largePicURL, boolean inTopPage) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stars = stars;
        this.smallPicURL = smallPicURL;
        this.largePicURL = largePicURL;
        this.inTopPage = inTopPage;
    }

    public Items(Long id, String name, String description, Double price, int stars, String smallPicURL, String largePicURL, boolean inTopPage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stars = stars;
        this.smallPicURL = smallPicURL;
        this.largePicURL = largePicURL;
        this.inTopPage = inTopPage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getSmallPicURL() {
        return smallPicURL;
    }

    public void setSmallPicURL(String smallPicURL) {
        this.smallPicURL = smallPicURL;
    }

    public String getLargePicURL() {
        return largePicURL;
    }

    public void setLargePicURL(String largePicURL) {
        this.largePicURL = largePicURL;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }

    public boolean isInTopPage() {
        return inTopPage;
    }

    public void setInTopPage(boolean inTopPage) {
        this.inTopPage = inTopPage;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stars=" + stars +
                ", smallPicURL='" + smallPicURL + '\'' +
                ", largePicURL='" + largePicURL + '\'' +
                ", addedDate=" + addedDate +
                ", inTopPage=" + inTopPage +
                '}';
    }
}
