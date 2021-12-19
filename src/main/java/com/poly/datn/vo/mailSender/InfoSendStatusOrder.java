package com.poly.datn.vo.mailSender;

import com.poly.datn.vo.OrderManagementVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class InfoSendStatusOrder {
    public String name;
    public String phone;
    public String email;
    public String address;
    public Integer orderId;
    OrderManagementVO[] orderManagementVO;

}
