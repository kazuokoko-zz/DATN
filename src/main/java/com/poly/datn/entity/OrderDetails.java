package com.poly.datn.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Order_details")
public class OrderDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private Long price;
    @Basic
    @Column(name = "discount", nullable = false, precision = 0)
    private Long discount;




    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false ,updatable  = false)
    Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false ,updatable  = false)
    Product product;

    public Orders getOrders() {
        return orders;
    }

    public Product getProduct() {
        return product;
    }
}
