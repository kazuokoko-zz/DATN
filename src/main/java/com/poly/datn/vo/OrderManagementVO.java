package com.poly.datn.vo;


import lombok.Data;

@Data
public class OrderManagementVO {

    private Long id;

    private Integer orderId;

    private String timeChange;

    private String changedBy;

    private String status;

    private String note;


    AccountVO account;

    OrdersVO orders;


}
