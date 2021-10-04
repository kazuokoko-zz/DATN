package com.poly.datn.VO;

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
