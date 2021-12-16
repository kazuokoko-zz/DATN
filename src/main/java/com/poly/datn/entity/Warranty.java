package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "warranty")
public class Warranty {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Basic
    @Column(name = "product_seri", nullable = false, length = 20)
    private String productSeri;
    @Basic
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Basic
    @Column(name = "color_id", nullable = false)
    private Integer colorId;
    @Basic
    @Column(name = "expired_date", nullable = false)
    private Date expiredDate;
    @Basic
    @Column(name = "status", nullable = false)
    private Integer status;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "count_warranty")
    private Integer countWarranty;
    @Basic
    @Column(name = "create_by", nullable = false)
    private String createBy;
    @Basic
    @Column(name = "create_date", nullable = false)
    private Date createDate;


    @OneToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    @JsonIgnore
    Orders orders;

    public Orders getOrders() {
        return orders;
    }
}
