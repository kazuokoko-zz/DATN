package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "category")
    List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "type", insertable = false ,updatable  = false)
    @JsonIgnore
    Category category;




    @OneToMany(mappedBy = "category")
    List<ProductCategory> productCategory;


    public List<Category> getCategories() {
        return categories;
    }

    public Category getCategory() {
        return category;
    }

    public List<ProductCategory> getProductCategory() {
        return productCategory;
    }

}
