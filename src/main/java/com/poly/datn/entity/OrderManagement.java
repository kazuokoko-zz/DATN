package com.poly.datn.entity;


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
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Column(name = "time_change", nullable = false)
    private Timestamp timeChange;
    @Column(name = "changed_by", nullable = false, length = 30)
    private String changedBy;
    @Column(name = "status", nullable = false, length = 40)
    private String status;
    @Column(name = "note", length = 100)
    private String note;


    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    Orders orders;

}
