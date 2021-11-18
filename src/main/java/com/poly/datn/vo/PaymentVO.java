package com.poly.datn.vo;

import lombok.Data;

@Data
public class PaymentVO {
    private Integer id;
    private String bankCode;
    private String bankTranNo;
    private String cardType;
    private String createDate;
    private String orderInfo;
    private String transactionNo;
    private String txnRef;
    private Integer ordersId;
    private Integer status;
}
