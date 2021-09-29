package com.poly.datn.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Orders {
    private int id;
    private Timestamp dateCreated;
    private String username;
    private long customerId;
    private double sumprice;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_created", nullable = false)
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "customer_id", nullable = false)
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "sumprice", nullable = false, precision = 0)
    public double getSumprice() {
        return sumprice;
    }

    public void setSumprice(double sumprice) {
        this.sumprice = sumprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id == orders.id && customerId == orders.customerId && Double.compare(orders.sumprice, sumprice) == 0 && Objects.equals(dateCreated, orders.dateCreated) && Objects.equals(username, orders.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreated, username, customerId, sumprice);
    }
}
