package com.poly.datn.vo.VoBoSung.ProductDTO;

import lombok.Data;

import javax.validation.Valid;

@Valid
@Data
public class UpdateProductCategoryDTO {

    private Long id;

    private Integer productId;

    private Integer categoryId;

}
