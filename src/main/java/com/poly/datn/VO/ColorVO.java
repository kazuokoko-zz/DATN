package com.poly.datn.VO;

import lombok.Data;

import java.util.List;

@Data
public class ColorVO {

    private Integer id;

    private String colorName;



    List<ProductColorVO> productColors;


}
