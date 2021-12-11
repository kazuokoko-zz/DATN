package com.poly.datn.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Orders {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "date_created", nullable = false)
    private Timestamp dateCreated;
    @Basic
    @Column(name = "username", length = 30)
    private String username;
    @Basic
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Basic
    @Column(name = "sumprice", nullable = false, precision = 0)
    private Long sumprice;
    @Basic
    @Column(name = "type_payment", nullable = false, precision = 0)
    private Boolean typePayment;

//    @OneToOne
//    @JoinColumn(name = "customer_id", insertable = false ,updatable  = false)
//    Customer customer;

    @OneToMany(mappedBy = "orders")
    List<OrderManagement> orderManagements;
    @OneToMany(mappedBy = "orders")
    List<OrderDetails> orderDetails;
    @OneToOne(mappedBy = "orders")
    Warranty warranty;


}
