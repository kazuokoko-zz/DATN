package com.poly.datn.service.impl;

import com.poly.datn.dao.OrdersDAO;
import com.poly.datn.entity.Orders;
import com.poly.datn.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServicesImpl implements OrdersService {
    @Autowired
    OrdersDAO ordersDAO;

    @Override
    public Orders getById(Integer id) {
        Orders orders = ordersDAO.findById(id).get();
        orders.getCustomer();
        return orders;
    }

    @Override
    public List<Orders> getByUsername(String username) {
        return ordersDAO.getByUsername(username);
    }
}
