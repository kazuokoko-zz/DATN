package com.poly.datn.service;

import com.poly.datn.entity.Orders;

import java.util.List;

public interface OrdersService {
    List<Orders> getByUsername(String username);

    Orders getById(Integer id);
}
