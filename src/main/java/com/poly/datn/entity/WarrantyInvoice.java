package com.poly.datn.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "warranty_invoice")
public class WarrantyInvoice {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "warranty_id")
    private Integer invoice;
    @Basic
    @Column(name = "price")
    private Long price;
    @Basic
    @Column(name = "product_state")
    private String productState;
    @Basic
    @Column(name = "type", nullable = false)
    private Boolean type;


}
