package com.example.khalilovf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
        private Attachment attachment;

    @ManyToOne(optional = false)
    private Supplier supplier;

    @ManyToMany
    //(cascade = CascadeType.ALL) manashuni qo'ysam deleted qila olmadim  esdan chiqmasin
    private List<Detail> details;

    private boolean active;

    @Column(nullable = false)
    private Double price;

    private String description;
}

