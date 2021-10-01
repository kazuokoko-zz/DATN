package com.poly.datn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }



    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    public Category getCategory() {
        return category;
    }

    public Product getProduct() {
        return product;
    }
}
