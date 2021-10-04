package com.poly.datn.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrdersVO {

    private Integer id;

    private Timestamp dateCreated;

    private String username;

    private Long customerId;

    private Double sumprice;


    AccountVO account;


    CustomerVO customer;


    List<OrderManagementVO> orderManagements;

    List<OrderDetailsVO> orderDetails;

    WarrantyVO warranty;


}
