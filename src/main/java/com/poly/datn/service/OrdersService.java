package com.poly.datn.service;

import com.poly.datn.VO.OrdersVO;

import java.util.List;

public interface OrdersService {
    List<OrdersVO> getByUsername(String username);

    OrdersVO getById(Integer id);
}
