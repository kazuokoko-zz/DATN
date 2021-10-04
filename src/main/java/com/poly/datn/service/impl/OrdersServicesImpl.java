package com.poly.datn.service.impl;

import com.poly.datn.VO.CustomerVO;
import com.poly.datn.VO.OrdersVO;
import com.poly.datn.dao.CustomerDAO;
import com.poly.datn.dao.OrdersDAO;
import com.poly.datn.entity.Customer;
import com.poly.datn.entity.Orders;
import com.poly.datn.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServicesImpl implements OrdersService {
    @Autowired
    OrdersDAO ordersDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Override
    public OrdersVO getById(Integer id) {
        Orders orders = ordersDAO.findById(id).get();
        Customer customer = customerDAO.findById(orders.getCustomerId()).get();
        OrdersVO ordersVO = new OrdersVO();
        CustomerVO vo = new CustomerVO();
        BeanUtils.copyProperties(customer, vo);
        BeanUtils.copyProperties(orders, ordersVO);
        ordersVO.setCustomer(vo);
        return ordersVO;
    }

    @Override
    public List<OrdersVO> getByUsername(String username) {
        List<OrdersVO> ordersVOS = new ArrayList<>();
        ordersDAO.getByUsername(username).forEach(orders -> {
            OrdersVO vo = new OrdersVO();
            BeanUtils.copyProperties(orders, vo);
            ordersVOS.add(vo);
        });
        return ordersVOS;
    }
}
