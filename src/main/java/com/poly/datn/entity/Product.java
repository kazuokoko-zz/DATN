package com.poly.datn.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    private Long price;
    @Basic
    @Column(name = "status", nullable = false, length = 20)
    private String status;



    @OneToMany(mappedBy = "product")
    List<Favorite> favorites;
    @OneToMany(mappedBy = "product")
    List<OrderDetails> orderDetails;
    @OneToMany(mappedBy = "product")
    List<ProductCategory> productCategories;
    @OneToMany(mappedBy = "product")
    List<ProductSale> productSales;
    @OneToMany(mappedBy = "product")
    List<ProductColor> productColors;
    @OneToMany(mappedBy = "product")
    List<CartDetail> cartDetails;
    @OneToMany(mappedBy = "product")
    List<ProductDetails> productDetails;

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }


    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public List<ProductSale> getProductSales() {
        return productSales;
    }

    public List<ProductColor> getProductColors() {
        return productColors;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public List<ProductDetails> getProductDetails() {
        return productDetails;
    }
}
