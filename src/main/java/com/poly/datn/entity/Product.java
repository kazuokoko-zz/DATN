package com.poly.datn.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Product {
    private Integer id;
    private String name;
    private Double price;
    private String status;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "status", nullable = false, length = 20)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @OneToMany(mappedBy = "product")
    List<OrderDetails> orderDetails;
    @OneToMany(mappedBy = "product")
    List<Blog> blogs;
    @OneToMany(mappedBy = "product")
    List<ProductCategory> productCategories;
    @OneToMany(mappedBy = "product")
    List<Sale> sales;
    @OneToMany(mappedBy = "product")
    List<ProductColor> productColors;
    @OneToMany(mappedBy = "product")
    List<CartDetail> cartDetails;
    @OneToMany(mappedBy = "product")
    List<ProductDetails> productDetails;

}
