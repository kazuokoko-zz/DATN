package com.poly.datn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }



    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    public Product getProduct() {
        return product;
    }
}
