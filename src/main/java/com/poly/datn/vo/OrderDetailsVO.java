package com.poly.datn.vo;

import com.poly.datn.vo.VoBoSung.OrderDTO.OrdersVO;
import lombok.Data;



@Data
public class OrderDetailsVO {

    private Long id;

    private Integer orderId;

    private Integer productId;

    private String productName;

    private Integer quantity;

    private Long price;

    private Long discount;

    private Integer colorId;

    private Integer colorName;

    private Integer warranty;

    private Boolean statusWarranty;


    OrdersVO orders;


    ProductVO product;


}
