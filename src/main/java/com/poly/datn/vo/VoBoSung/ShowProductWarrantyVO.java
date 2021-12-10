package com.poly.datn.vo.VoBoSung;

import com.poly.datn.vo.CustomerVO;
import com.poly.datn.vo.ProductVO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ShowProductWarrantyVO {
    private Integer id;

    private Timestamp dateCreated;

    private String username;

    private Long customerId;

    private Long sumprice;

    private String status;

    private Boolean typePayment;

    CustomerVO customer;
    List<ProductVO> productVOS;

}
