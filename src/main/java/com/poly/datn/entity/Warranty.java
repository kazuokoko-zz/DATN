package com.poly.datn.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Warranty {
    private int id;
    private int orderId;
    private String productSeri;
    private int productId;
    private Date expiredDate;
    private byte status;

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
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warranty warranty = (Warranty) o;
        return id == warranty.id && orderId == warranty.orderId && productId == warranty.productId && status == warranty.status && Objects.equals(productSeri, warranty.productSeri) && Objects.equals(expiredDate, warranty.expiredDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, productSeri, productId, expiredDate, status);
    }
}
