package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Product_color")
public class ProductColor {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Basic
    @Column(name = "color_id", nullable = false)
    private Integer colorId;
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false ,updatable  = false)
    @JsonIgnore
    Product product;
    @ManyToOne
    @JoinColumn(name = "color_id", insertable = false ,updatable  = false)
    @JsonIgnore
    Color color;

    @OneToMany(mappedBy = "productColor")
    List<QuantityManagerment> quantityManagerments;

    public Product getProduct() {
        return product;
    }

    public Color getColor() {
        return color;
    }

    public List<QuantityManagerment> getQuantityManagerments() {
        return quantityManagerments;
    }
}
