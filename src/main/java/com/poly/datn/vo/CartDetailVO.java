package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import com.poly.datn.vo.VoBoSung.Account.AccountVO;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CartDetailVO extends RegexEmail{
    private Integer id;

    private Integer userId;

    @NotNull(message = "Mã sản phẩm không được để trống")
    private Integer productId;


    private String productName;

    private String photo;

    @NotNull(message = "Số lượng sản phẩm không được để trống")
    private Integer quantity;

    private Long price;

    private Long discount;

    @NotNull(message = "Màu sắc sản phẩm không được để trống")
    private Integer colorId;

    private String colorName;

    AccountVO account;

    ProductVO product;


}
