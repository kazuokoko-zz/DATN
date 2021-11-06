package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
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
    private Long sumprice;


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
