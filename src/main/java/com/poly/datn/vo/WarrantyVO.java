package com.poly.datn.vo;


import com.poly.datn.common.RegexEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class WarrantyVO extends RegexEmail {

    private Integer id;

    @NotNull
    private Integer orderId;

    @NotNull
    private String productSeri;

    @NotNull
    private Integer productId;
    @NotNull
    private Integer colorId;

    @NotNull
    private Date expiredDate;

    private String name;

    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH)
    private String phone;

    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH)
    private String address;

    private Integer countWarranty;

    private Integer status;

    OrdersVO orders;

}
