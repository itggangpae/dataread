package com.adamsoft.dataread;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name= "book")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bid;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 50, nullable = false)
    private String author;
    @Column(length = 50, nullable = false)
    private String category;
    @Column
    private int pages;
    @Column
    private int price;
    @Column
    private Date published_date;
    @Column(length = 50, nullable = false)
    private String description;
}
