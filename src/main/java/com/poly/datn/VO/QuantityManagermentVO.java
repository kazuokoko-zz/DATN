package com.poly.datn.VO;


import lombok.Data;


import java.sql.Timestamp;

@Data
public class QuantityManagermentVO {

    private Integer id;

    private Integer productColorId;

    private Integer changedBy;

    private Timestamp timeChanged;

    private String note;


    AccountVO account;

    ProductColorVO productColor;


}
