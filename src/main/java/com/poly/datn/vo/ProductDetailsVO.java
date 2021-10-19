package com.poly.datn.vo;

import lombok.Data;



@Data
public class ProductDetailsVO {

    private Long id;

    private Integer productId;

    private String propertyName;

    private String propertyValue;


    ProductVO product;


}
