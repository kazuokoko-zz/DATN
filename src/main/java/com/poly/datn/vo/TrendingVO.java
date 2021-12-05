package com.poly.datn.vo;

import lombok.Data;

@Data
public class TrendingVO {
    ProductVO productVO;
    Integer quantity;

    public TrendingVO(ProductVO productVO, Integer quantity) {
        this.quantity = quantity;
        this.productVO = productVO;
    }
}
