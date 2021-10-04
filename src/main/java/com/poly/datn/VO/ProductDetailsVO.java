package com.poly.datn.VO;

import lombok.Data;



@Data
public class ProductDetailsVO {

    private Long id;

    private Integer productId;

    private String propertyName;

    private String propertyValue;


    ProductVO product;


}
