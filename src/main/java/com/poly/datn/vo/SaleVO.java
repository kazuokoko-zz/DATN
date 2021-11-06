package com.poly.datn.vo;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
public class SaleVO {


    private Integer id;
    private String name;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;


    ProductVO product;

}
