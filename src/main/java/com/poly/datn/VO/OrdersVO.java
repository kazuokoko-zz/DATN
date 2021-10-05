package com.poly.datn.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrdersVO {

    private Integer id;

    private Timestamp dateCreated;

    private String username;

    private Long customerId;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER,pattern = "##.##")
    private Double sumprice;


    CustomerVO customer;


    List<OrderManagementVO> orderManagements;

    List<OrderDetailsVO> orderDetails;

    WarrantyVO warranty;


}
