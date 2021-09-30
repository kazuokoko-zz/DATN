package com.poly.datn.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Warranty {
    private Integer id;
    private Integer orderId;
    private String productSeri;
    private Integer productId;
    private Date expiredDate;
    private Boolean status;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "product_seri", nullable = false, length = 20)
    public String getProductSeri() {
        return productSeri;
    }

    public void setProductSeri(String productSeri) {
        this.productSeri = productSeri;
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
    @Column(name = "expired_date", nullable = false)
    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }



    @OneToOne
    @JoinColumn(name = "order_id")
    Orders orders;
}
