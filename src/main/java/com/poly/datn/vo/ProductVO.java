package com.poly.datn.vo;

import lombok.Data;


import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductVO {

    private Integer id;
    @NotNull(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotNull(message = "Giá sản phẩm không được để trống")
    private Long price;
    @NotNull(message = "Thời gian bảo hành không được để trống")
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
