package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
public class CartDetailVO extends RegexEmail{
    private Integer id;

    private Integer userId;

    @NotNull
    private Integer productId;

    @NotNull
    private String productName;

    private String photo;

    @NotNull
    private Integer quantity;

    @NotNull
    private Long price;

    @NotNull
    private Long priceBefforSale;

    @NotNull
    private Long discount;
    @NotNull
    private Integer colorId;

    AccountVO account;

    ProductVO product;


}
