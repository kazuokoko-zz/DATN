package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Basic
    @Column(name = "type", nullable = false)
    private Integer type;
    @Basic
    @Column(name = "status", nullable = false)
    private Boolean status;


    @OneToMany(mappedBy = "fatherCate")
    List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "type", insertable = false, updatable = false)
    @JsonIgnore
    Category fatherCate;


    @OneToMany(mappedBy = "category")
    List<ProductCategory> productCategory;

    public List<Category> getCategories() {
        return categories;
    }

    public Category getFatherCate() {
        return fatherCate;
    }

    public List<ProductCategory> getProductCategory() {
        return productCategory;
    }
}
