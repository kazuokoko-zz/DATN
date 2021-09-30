package com.poly.datn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Product_details", schema = "bhoddvjk1na7d8a0xtlr")
public class ProductDetails {
    private Long id;
    private Integer productId;
    private String propertyName;
    private String propertyValue;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "property_name", nullable = false, length = 30)
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Basic
    @Column(name = "property_value", nullable = false, length = 150)
    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }



    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
