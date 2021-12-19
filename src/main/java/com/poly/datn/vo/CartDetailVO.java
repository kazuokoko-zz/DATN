package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import com.poly.datn.vo.VoBoSung.Account.AccountVO;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CartDetailVO extends RegexEmail{
    private Integer id;

    private Integer userId;

    @NotNull
    private Integer productId;


    private String productName;

    private String photo;

    @NotNull
    private Integer quantity;


    private Long price;

//    @NotNull
//    private Long priceBefforSale;


    private Long discount;

    @NotNull
    private Integer colorId;

    private String colorName;

    AccountVO account;

    ProductVO product;


}
