package com.poly.datn.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Category {
    private Integer id;
    private String name;
    private Integer type;
    private Boolean status;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "category")
    List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "type")
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
