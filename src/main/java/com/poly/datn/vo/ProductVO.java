package com.poly.datn.vo;

import lombok.Data;


import java.util.List;

@Data
public class ProductVO {

    private Integer id;

    private String name;

    private Long price;

    private Integer warranty;

    private List<String> photos;

    private String status;

    private Long Discount;


    List<OrderDetailsVO> orderDetails;

    BlogVO blogs;

    List<ProductCategoryVO> productCategories;

    List<SaleVO> sales;

    List<ProductColorVO> productColors;

    List<CartDetailVO> cartDetails;

    List<ProductDetailsVO> productDetails;


}
