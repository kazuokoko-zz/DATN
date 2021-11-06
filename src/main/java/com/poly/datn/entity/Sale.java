package com.poly.datn.entity;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Entity
@Table(name = "Sale")
@Data
public class Sale {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;
    @Basic
    @Column(name = "end_time", nullable = false)
    private Timestamp endTime;
    @Basic
    @Column(name = "status", nullable = false, length = 20)
    private String status;


    @OneToMany(mappedBy = "sale")
    List<ProductSale> productSales;

    public List<ProductSale> getProductSale() {
        return productSales;
    }
}
