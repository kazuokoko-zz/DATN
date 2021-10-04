package com.poly.datn.VO;


import lombok.Data;

import java.sql.Timestamp;
@Data
public class SaleVO {

    private Integer id;

    private Integer productId;

    private Double discount;

    private Timestamp startTime;

    private Timestamp endTime;

    private Integer quantity;

    private String status;


    ProductVO product;

}
