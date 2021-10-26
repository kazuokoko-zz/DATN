package com.poly.datn.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Product")
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
    private Double price;
    @Basic
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



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
