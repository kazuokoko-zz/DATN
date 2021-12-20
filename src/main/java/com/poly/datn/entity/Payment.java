package com.poly.datn.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "bankcode", length = 20)
    private String bankCode;
    @Column(name = "banktranno", length = 20)
    private String bankTranNo;
    @Column(name = "cardtype", length = 20)
    private String cardType;
    @Column(name = "createdate", length = 14, nullable = false)
    private String createDate;
    @Column(name = "paydate", length = 14)
    private String payDate;
    @Column(name = "orderinfo", length = 20, nullable = false)
    private String orderInfo;
    @Column(name = "transactionno", length = 20)
    private String transactionNo;
    @Column(name = "txnref", length = 10, nullable = false)
    private String txnRef;
    @Column(name = "orders_id", nullable = false)
    private Integer ordersId;
    @Column(name = "status", nullable = false)
    private Integer status;

}
