package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "date_created", nullable = false)
    private Timestamp dateCreated;
    @Basic
    @Column(name = "username", nullable = true, length = 30)
    private String username;
    @Basic
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Basic
    @Column(name = "sumprice", nullable = false, precision = 0)
    private Double sumprice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public double getSumprice() {
        return sumprice;
    }

    public void setSumprice(double sumprice) {
        this.sumprice = sumprice;
    }



    @ManyToOne
    @JoinColumn(name = "username", insertable = false ,updatable  = false)
    @JsonIgnore
    Account account;

    @OneToOne
    @JoinColumn(name = "customer_id", insertable = false ,updatable  = false)
    @JsonIgnore
    Customer customer;

    @OneToMany(mappedBy = "orders")
    List<OrderManagement> orderManagements;
    @OneToMany(mappedBy = "orders")
    List<OrderDetails> orderDetails;
    @OneToOne(mappedBy = "orders")
    Warranty warranty;

    public Account getAccount() {
        return account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderManagement> getOrderManagements() {
        return orderManagements;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public Warranty getWarranty() {
        return warranty;
    }
}
