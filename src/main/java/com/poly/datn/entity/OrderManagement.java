package com.poly.datn.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class OrderManagement {
    private long id;
    private int orderId;
    private Timestamp timeChange;
    private String changedBy;
    private String status;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    @Column(name = "time_change", nullable = false)
    public Timestamp getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(Timestamp timeChange) {
        this.timeChange = timeChange;
    }

    @Basic
    @Column(name = "changed_by", nullable = false, length = 30)
    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    @Basic
    @Column(name = "status", nullable = false, length = 40)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderManagement that = (OrderManagement) o;
        return id == that.id && orderId == that.orderId && Objects.equals(timeChange, that.timeChange) && Objects.equals(changedBy, that.changedBy) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, timeChange, changedBy, status);
    }
}
