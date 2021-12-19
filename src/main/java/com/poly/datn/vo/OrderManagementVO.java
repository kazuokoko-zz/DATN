package com.poly.datn.vo;


import com.poly.datn.vo.VoBoSung.Account.AccountVO;
import com.poly.datn.vo.VoBoSung.OrderDTO.OrdersVO;
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
