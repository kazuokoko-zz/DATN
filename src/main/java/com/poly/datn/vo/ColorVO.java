package com.poly.datn.vo;

import lombok.Data;

import java.util.List;

@Data
public class ColorVO {

    private Integer id;

    private String colorName;

    private String code;



    List<ProductColorVO> productColors;


}
