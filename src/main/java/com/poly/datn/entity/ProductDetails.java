package com.poly.datn.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Product_details")
public class ProductDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Basic
    @Column(name = "property_name", nullable = false, length = 30)
    private String propertyName;
    @Basic
    @Column(name = "property_value", nullable = false, length = 150)
    private String propertyValue;

   

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false ,updatable  = false)
    Product product;

    public Product getProduct() {
        return product;
    }
}
