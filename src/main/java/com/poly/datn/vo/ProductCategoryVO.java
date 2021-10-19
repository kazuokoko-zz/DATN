package com.poly.datn.vo;

import lombok.Data;

@Data
public class ProductCategoryVO {

    private Long id;

    private Integer productId;

    private Integer categoryId;


    CategoryVO category;

    ProductVO product;


}
