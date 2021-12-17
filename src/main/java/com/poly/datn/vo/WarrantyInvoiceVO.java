package com.poly.datn.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;


@Data
public class WarrantyInvoiceVO {

    private Integer id;

    private Long price;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    private Integer warrantyId;

    private String productSeri;

    private Integer productId;

    private Integer colorId;

    private Date expiredDate;

    private String warrantyUnit;

    private String createBy;

    private String productState;

    private String type;
}
