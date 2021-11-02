package com.poly.datn.vo;

import lombok.Data;



@Data
public class CustomerVO {

    private Long id;

    private String fullname;

    private String email;

    private String phone;

    private String address;

    private String note;

    OrdersVO orders;


}
