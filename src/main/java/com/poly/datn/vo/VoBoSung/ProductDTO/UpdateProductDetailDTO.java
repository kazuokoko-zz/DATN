package com.poly.datn.vo.VoBoSung.ProductDTO;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@Data
public class UpdateProductDetailDTO {

    private Long id;
    @NotNull(message = "Mã sản phẩm không được để trống")
    private Integer productId;
    @NotNull(message = "tên trường không được để trống")
    private String propertyName;
    @NotNull(message = "giá trị trường không được để trống")
    private String propertyValue;

}
