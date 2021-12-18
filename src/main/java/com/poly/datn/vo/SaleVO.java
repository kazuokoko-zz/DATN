package com.poly.datn.vo;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
public class SaleVO {


    private Integer id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private Timestamp startTime;
    @NotNull
    private Timestamp endTime;
    private String status;

    List<ProductSaleVO> productSaleVO;

}
