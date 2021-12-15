package com.poly.datn.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrdersVO {

    private Integer id;

    private Timestamp dateCreated;

    private String username;

    private Long customerId;

    private Long sumprice;

    private String status;

    private Boolean typePayment;

    private Integer numOfProduct;

    CustomerVO customer;

    List<OrderManagementVO> orderManagements;

    List<OrderDetailsVO> orderDetails;

    WarrantyVO warranty;


}
