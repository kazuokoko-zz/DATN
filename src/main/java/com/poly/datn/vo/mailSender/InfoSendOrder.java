package com.poly.datn.vo.mailSender;

import com.poly.datn.vo.OrderDetailsVO;
import lombok.Data;

import java.util.List;

@Data
public class InfoSendOrder {
    public Long totalPrice;
    public Long price;
    public Long discount;

    public String name;
    public String phone;
    public String email;
    public String address;
    List<OrderDetailsVO> orderDetails;
}
