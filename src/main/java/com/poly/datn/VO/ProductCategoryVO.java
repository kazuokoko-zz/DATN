package com.poly.datn.VO;

import lombok.Data;

import javax.persistence.*;

@Data
public class ProductCategoryVO {

    private Long id;

    private Integer productId;

    private Integer categoryId;


    CategoryVO category;

    ProductVO product;


}
