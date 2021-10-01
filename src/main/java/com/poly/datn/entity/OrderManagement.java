package com.poly.datn.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class OrderManagement {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Basic
    @Column(name = "time_change", nullable = false)
    private Timestamp timeChange;
    @Basic
    @Column(name = "changed_by", nullable = false, length = 30)
    private String changedBy;
    @Basic
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(Timestamp timeChange) {
        this.timeChange = timeChange;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @ManyToOne
    @JoinColumn(name = "changed_by")
    Account account;
    @ManyToOne
    @JoinColumn(name = "order_id")
    Orders  orders;

    public Account getAccount() {
        return account;
    }

    public Orders getOrders() {
        return orders;
    }
}