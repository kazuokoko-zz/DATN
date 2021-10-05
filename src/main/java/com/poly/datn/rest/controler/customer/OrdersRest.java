package com.poly.datn.rest.controler.customer;

import com.poly.datn.Utils.Jwt;
import com.poly.datn.VO.OrdersVO;
import com.poly.datn.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/customer/orders")
public class OrdersRest {

    @Autowired
    OrdersService ordersService;

    @GetMapping
    public List<OrdersVO> getList(Principal principal) {
        return ordersService.getByUsername(principal.getName());
    }

    @GetMapping("{id}")
    public OrdersVO getOrders(Principal principal, @PathVariable("id") Integer id) throws Exception {
        return ordersService.getByIdAndUserName(id, principal.getName());
    }
}
