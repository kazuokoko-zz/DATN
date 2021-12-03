package com.poly.datn.service;

import com.poly.datn.vo.OrdersVO;

import java.security.Principal;
import java.util.List;

public interface ReportService {
    List<OrdersVO> getListUnComfirmOrders(Principal principal);
}
