package com.poly.datn.VO;

import lombok.Data;


@Data
public class CartDetailVO {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Double price;

    private Integer saleId;

    AccountVO account;

    ProductVO product;

}
