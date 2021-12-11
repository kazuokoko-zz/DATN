package com.poly.datn.service;

import com.poly.datn.vo.OrdersVO;
import com.poly.datn.vo.ProductVO;

import java.security.Principal;
import java.util.List;

public interface ReportService {
    List<OrdersVO> getListUnComfirmOrders(Principal principal);

    List<OrdersVO> getlistOrders(Principal principal);
    List<OrdersVO> getListCancerOrders(Principal principal);
    List<OrdersVO> getListSuccessOrders(Principal principal);
    List<OrdersVO> getListComfimOrders(Principal principal);
    List<OrdersVO> getListErrorOrders(Principal principal);

    Integer sumOrderInMonth(Principal principal);
    Integer sumCancerOrderInMonth(Principal principal);
    Integer sumSuccessOrderInMonth(Principal principal);
    Integer sumComfimOrder(Principal principal);
    Integer sumErrorOrder(Principal principal);

    List<ProductVO> getTrendingAdmin(Principal principal);

    Integer getNumberOfUnConfirmOrder(Principal principal);
}
