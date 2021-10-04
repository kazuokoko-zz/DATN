package com.poly.datn.rest.controler.customer;

import com.poly.datn.VO.OrdersVO;
import com.poly.datn.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer/orders")
public class OrdersRest {

    @Autowired
    OrdersService ordersService;

    @GetMapping("{id}/account/{u}")
    public List<OrdersVO> getList(@PathVariable("id") Integer id, @PathVariable("u") String u) {
        return ordersService.getByUsername(u);
    }

    @GetMapping("{id}/cutomer/{u}/{oid}")
    public OrdersVO getOrders(@PathVariable("id") Integer id, @PathVariable("u") String u, @PathVariable("oid") Integer oid) {
        return ordersService.getById(oid);
    }
}
