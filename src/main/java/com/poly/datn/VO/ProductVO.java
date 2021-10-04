package com.poly.datn.VO;

import lombok.Data;


import java.util.List;

 @Data
public class ProductVO {

    private Integer id;

    private String name;

    private Double price;

    private String status;



    List<OrderDetailsVO> orderDetails;

    List<BlogVO> blogs;

    List<ProductCategoryVO> productCategories;

    List<SaleVO> sales;

    List<ProductColorVO> productColors;

    List<CartDetailVO> cartDetails;

    List<ProductDetailsVO> productDetails;


}
