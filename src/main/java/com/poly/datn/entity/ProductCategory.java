package com.poly.datn.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Product_category")
public class ProductCategory {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Basic
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;




    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false ,updatable  = false)
    Category category;
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false ,updatable  = false)
    Product product;

    public Category getCategory() {
        return category;
    }

    public Product getProduct() {
        return product;
    }
}
