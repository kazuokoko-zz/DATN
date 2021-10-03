package com.poly.datn.rest.controler.customer;

import com.poly.datn.entity.CartDetail;
import com.poly.datn.entity.Orders;
import com.poly.datn.service.CartService;
import com.poly.datn.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer/cart")
public class CartDetailsRest {

    @Autowired
    CartService cartService;

    @GetMapping("/{id}")
    public List<CartDetail> getList(@PathVariable("id") Integer id) {
        return cartService.findCartByUsername(id);
    }
}
