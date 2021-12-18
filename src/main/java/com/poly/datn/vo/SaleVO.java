package com.poly.datn.vo;


import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class SaleVO {


    private Integer id;
    private String name;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;


    ProductVO product;
    List<ProductSaleVO> productSaleVO;

}
