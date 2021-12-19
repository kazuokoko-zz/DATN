package com.poly.datn.vo.VoBoSung.ProductDTO;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class UpdateProductColorDTO {
    private Integer id;
    @NotNull(message = "Mã sản phẩm không được để trống")
    private Integer productId;
    @NotNull(message = "Mã màu sản phẩm không được để trống")
    private Integer colorId;
    @NotNull(message = "Số lượng sản phẩm không được để trống")
    private Integer quantity;
}
