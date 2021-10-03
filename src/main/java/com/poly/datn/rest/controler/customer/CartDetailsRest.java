package com.poly.datn.rest.controler.customer;

import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/cart")
public class CartDetailsRest {

    @Autowired
    CartDetailService cartService;

    @GetMapping("get")
    public List<CartDetail> getList(@RequestParam("username") String username) {
        return cartService.findCartByUsername(username);
    }
}
