package com.poly.datn.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrdersVO {

    private Integer id;

    private Timestamp dateCreated;

    private String username;

    private Long customerId;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER,pattern = "##.##")
    private Long sumprice;

    CustomerVO customer;

    List<OrderManagementVO> orderManagements;

    List<OrderDetailsVO> orderDetails;

    WarrantyVO warranty;


}
