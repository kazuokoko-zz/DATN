package com.poly.datn.vo;

import lombok.Data;



@Data
public class OrderDetailsVO {

    private Long id;

    private Integer orderId;

    private Integer productId;

    private Integer quantity;

    private Long price;

    private Long discount;


    OrdersVO orders;


    ProductVO product;


}
