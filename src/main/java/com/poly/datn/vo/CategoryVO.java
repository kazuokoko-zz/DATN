package com.poly.datn.vo;

import lombok.Data;
import lombok.Setter;

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
