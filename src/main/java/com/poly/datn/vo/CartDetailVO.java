package com.poly.datn.vo;

import lombok.Data;


@Data
public class CartDetailVO {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Long price;

    private Integer saleId;

    AccountVO account;

    ProductVO product;


}
