package com.poly.datn.VO;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {

    private Integer id;

    private String name;

    private Integer type;

    private Boolean status;
    List<CategoryVO> categories;
    CategoryVO category;
    List<ProductCategoryVO> productCategory;
}
