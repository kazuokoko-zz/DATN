package com.poly.datn.vo.VoBoSung.OrderDTO;

import com.poly.datn.vo.CustomerVO;
import com.poly.datn.vo.OrderDetailsVO;
import com.poly.datn.vo.OrderManagementVO;
import com.poly.datn.vo.WarrantyVO;
import lombok.Data;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrdersVO {


    private Integer id;

    private Timestamp dateCreated;

    private String username;

    private Long customerId;

    private Long sumprice;

    private String status;

    private Boolean typePayment;

    private Integer numOfProduct;

    @Valid
    CustomerVO customer;

    List<OrderManagementVO> orderManagements;

    List<OrderDetailsVO> orderDetails;

    WarrantyVO warranty;


}
