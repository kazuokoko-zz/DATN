package com.poly.datn.vo.VoBoSung.ProductDTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckProductColorDTO {
    @NotNull(message = "Không được để trống mã sản phẩm")
    public Integer productId;
    @NotNull(message = "Không được để trống mã màu")
    public  Integer colorId;
}
