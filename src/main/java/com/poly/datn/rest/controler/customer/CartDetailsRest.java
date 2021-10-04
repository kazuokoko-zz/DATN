package com.poly.datn.rest.controler.customer;

import com.poly.datn.VO.CartDetailVO;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/cart")
public class CartDetailsRest {

    @Autowired
    CartDetailService cartService;

    @GetMapping("{u}")
    public List<CartDetailVO> getList(@PathVariable("u") String u) {
        return cartService.findCartByUsername(u);
    }

}
