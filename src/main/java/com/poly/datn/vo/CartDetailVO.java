package com.poly.datn.vo;

import lombok.Data;


@Data
public class CartDetailVO {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private String productName;

    private String photo;

    private Integer quantity;

    private Long price;
    private Long priceBefforSale;

    private Long discount;

    private Integer colorId;




    AccountVO account;

    ProductVO product;


}
