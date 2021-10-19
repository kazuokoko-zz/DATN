package com.poly.datn.vo;

import lombok.Data;



@Data
public class OrderDetailsVO {

    private Long id;

    private Integer orderId;

    private Integer productId;

    private Integer quantity;

    private Double price;

    private Double discount;


    OrdersVO orders;


    ProductVO product;


}
