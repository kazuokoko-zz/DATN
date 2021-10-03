package com.poly.datn.rest.controler.customer;

import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
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
    CartDetailService cartService;

    @GetMapping("/{username}")
    public List<CartDetail> getList(@PathVariable("username") String username) {
        return cartService.findCartByUsername(username);
    }
}
