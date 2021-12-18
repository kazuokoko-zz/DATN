package com.poly.datn.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ProductSaleVO {

    private Integer id;
    @NotNull
    private Integer productId;
    @NotNull
    private Integer saleId;
    @NotNull
    @Pattern(regexp = "[0-9]+", message = "Không phải là số")
    private Long discount;
    @NotNull
    @Pattern(regexp = "[0-9]+", message = "Không phải là số")
    private Integer quantity;


    ProductVO productVO;

    SaleVO saleVO;
}
