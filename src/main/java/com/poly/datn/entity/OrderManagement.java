package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "ordermanagement")
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



    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false ,updatable  = false)
    @JsonIgnore
    Orders  orders;


    public Orders getOrders() {
        return orders;
    }
}
