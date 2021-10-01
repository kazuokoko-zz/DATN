package com.poly.datn.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Warranty {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Basic
    @Column(name = "product_seri", nullable = false, length = 20)
    private String productSeri;
    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Basic
    @Column(name = "expired_date", nullable = false)
    private Date expiredDate;
    @Basic
    @Column(name = "status", nullable = false)
    private Boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductSeri() {
        return productSeri;
    }

    public void setProductSeri(String productSeri) {
        this.productSeri = productSeri;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }



    @OneToOne
    @JoinColumn(name = "order_id")
    Orders orders;

    public Orders getOrders() {
        return orders;
    }
}
