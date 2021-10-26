package com.poly.datn.vo;

import lombok.Data;

@Data
public class ProductSaleVO {

    private Integer id;
    private Integer productId;
    private Integer saleId;
    private Integer discount;
    private Integer quantity;


    ProductVO productVO;

    SaleVO saleVO;
}
