package com.poly.datn.VO;


import lombok.Data;


import java.util.List;

@Data
public class ProductColorVO {

    private Integer id;

    private Integer productId;

    private Integer colorId;

    private Integer quantity;


    ProductVO product;

    ColorVO color;


    List<QuantityManagermentVO> quantityManagerments;


}
