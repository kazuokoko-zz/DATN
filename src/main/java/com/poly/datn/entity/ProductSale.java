package com.poly.datn.entity;



import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_sale")
@Data
public class ProductSale {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Basic
    @Column(name = "sale_id", nullable = false)
    private Integer saleId;
    @Basic
    @Column(name = "discount", nullable = false)
    private Long discount;
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "sale_id", insertable = false, updatable = false)
    Sale sale;

    public Product getProduct() {
        return product;
    }

    public Sale getSale() {
        return sale;
    }
}
