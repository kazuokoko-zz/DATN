package com.poly.datn.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
public class Orders {
    private Integer id;
    private Timestamp dateCreated;
    private String username;
    private Long customerId;
    private Double sumprice;

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



    @ManyToOne
    @JoinColumn(name = "username")
    Account account;

    @OneToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToMany(mappedBy = "orders")
    List<OrderManagement> orderManagements;
    @OneToMany(mappedBy = "orders")
    List<OrderDetails> orderDetails;
    @OneToOne(mappedBy = "orders")
    Warranty warranty;
}
