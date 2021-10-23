package com.poly.datn.entity;


import javax.persistence.*;

@Entity
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
    private Double price;
    @Basic
    @Column(name = "discount", nullable = false, precision = 0)
    private Double discount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


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
