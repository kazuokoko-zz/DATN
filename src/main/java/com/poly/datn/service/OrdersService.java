package com.poly.datn.service;

import com.poly.datn.entity.Orders;
import com.poly.datn.vo.CustomerVO;
import com.poly.datn.vo.OrderDetailsVO;
import com.poly.datn.vo.OrdersVO;

import java.security.Principal;
import java.util.List;

public interface OrdersService {
    List<OrdersVO> getByUsername(Principal principal);

    List<OrdersVO> getAll(Principal principal);

    OrdersVO getByIdAndUserName(Integer id, String username) throws SecurityException;
//, OrderDetailsVO orderDetailsVO, CustomerVO customerVO
    OrdersVO newOrder(OrdersVO ordersVO, Principal principal);
}
