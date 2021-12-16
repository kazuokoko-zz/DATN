package com.poly.datn.vo.mailSender;

import com.poly.datn.entity.Payment;
import com.poly.datn.vo.OrderDetailsVO;
import lombok.Data;

@Data
public class InfoSendOrder {
    public Long totalPrice;
    public Long price;
    public Long discount;

    public String name;
    public String phone;
    public String email;
    public String address;
    OrderDetailsVO[] orderDetails;
    Payment payment;
}
