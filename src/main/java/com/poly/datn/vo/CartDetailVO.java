package com.poly.datn.vo;

import lombok.Data;


@Data
public class CartDetailVO {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private String productName;

    private Integer quantity;

    private Long price;

    private Integer discount;

    AccountVO account;

    ProductVO product;


}
