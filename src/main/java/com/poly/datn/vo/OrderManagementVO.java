package com.poly.datn.vo;


import lombok.Data;


import java.sql.Timestamp;

@Data
public class OrderManagementVO {

    private Long id;

    private Integer orderId;

    private Timestamp timeChange;

    private String changedBy;

    private String status;


    AccountVO account;

    OrdersVO orders;


}
