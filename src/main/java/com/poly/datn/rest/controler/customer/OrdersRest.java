package com.poly.datn.rest.controler.customer;

import com.poly.datn.vo.OrdersVO;
import com.poly.datn.common.Constant;
import com.poly.datn.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
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
