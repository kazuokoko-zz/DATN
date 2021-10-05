package com.poly.datn.rest.controler.customer;

import com.poly.datn.VO.CartDetailVO;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/customer/cart")
public class CartDetailsRest {

    @Autowired
    CartDetailService cartDetailService;

    @GetMapping("get")
    public List<CartDetailVO> getList(Principal principal) {
        return cartDetailService.findCartByUsername(principal.getName());
    }
    @PutMapping("update")
    public CartDetailVO updateCartDetail(@RequestBody CartDetailVO cartDetailVO) {
        return cartDetailService.getCartDetailById(cartDetailVO);
    }
    @DeleteMapping("delete/{id}")
    public CartDetailVO deleteCartDetail(@PathVariable Integer id) {
        return cartDetailService.deleteCartDetaiilById(id);
    }
    @DeleteMapping("{username}/addtocart/{id}")
    public CartDetailVO addToCartDetail(Integer id, Principal principal) {
        return cartDetailService.newCartDetaiilByUsername(id, principal.getName());
    }
}
