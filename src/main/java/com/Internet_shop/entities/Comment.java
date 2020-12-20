package com.Internet_shop.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name="addedDate", columnDefinition="timestamp NOT NULL DEFAULT NOW()", insertable = false, updatable = false)
    private Timestamp addedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    Items items;

    @ManyToOne(fetch = FetchType.EAGER)
    Users author;
}
