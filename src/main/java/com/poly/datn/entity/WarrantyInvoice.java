package com.poly.datn.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "warranty_invoice")
public class WarrantyInvoice {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "price")
    private Long price;

    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "warranty_id", nullable = false)
    private Integer warrantyId;
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
    @Column(name = "warranty_unit", nullable = false)
    private String warrantyUnit;
    @Basic
    @Column(name = "create_by", nullable = false)
    private String createBy;
    @Basic
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Basic
    @Column(name = "product_state")
    private String productState;
    @Basic
    @Column(name = "type", nullable = false)
    private String type;


}
