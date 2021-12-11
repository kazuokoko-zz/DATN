package com.poly.datn.vo;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class WarrantyVO {

    private Integer id;

    @NotNull
    private Integer orderId;

    @NotNull
    private String productSeri;

    @NotNull
    private Integer productId;

    private Date expiredDate;

    private Integer status;

    private String name;

    private String phone;

    private String address;


    OrdersVO orders;

}
