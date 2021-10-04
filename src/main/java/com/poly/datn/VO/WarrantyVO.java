package com.poly.datn.VO;


import lombok.Data;

import java.sql.Date;

@Data
public class WarrantyVO {

    private Integer id;

    private Integer orderId;

    private String productSeri;

    private Integer productId;

    private Date expiredDate;

    private Boolean status;


    OrdersVO orders;

}
