package com.poly.datn.service;

import com.poly.datn.vo.OrdersVO;
import com.poly.datn.vo.ProductVO;

import java.security.Principal;
import java.util.List;

public interface ReportService {
    List<OrdersVO> getListUnComfirmOrders(Principal principal);

    Integer sumOrderInMonth(Principal principal);
    Integer sumCancerOrderInMonth(Principal principal);
    Integer sumSuccessOrderInMonth(Principal principal);
    Integer sumComfimOrder(Principal principal);

    List<ProductVO> getTrendingAdmin(Principal principal);

    List<OrdersVO> getListCancerOrderInMonth(Principal principal);
    List<OrdersVO> getListComfimOrderInMonth(Principal principal);

    Integer getNumberOfUnConfirmOrder(Principal principal);
}
