package com.poly.datn.rest.controler.customer;

import com.poly.datn.vo.CartDetailVO;
import com.poly.datn.common.Constant;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/cart")
public class CartDetailsRest {

    @Autowired
    CartDetailService cartDetailService;



    @GetMapping("get")
    public List<CartDetailVO> getList(Principal principal) {
        return cartDetailService.findCartByUsername(principal);
    }

    @PutMapping("update")
    public CartDetailVO updateCartDetail(@RequestBody CartDetailVO cartDetailVO, Principal principal) {
        return cartDetailService.save(cartDetailVO, principal);
    }
    @DeleteMapping("delete/{id}")
    public boolean deleteCartDetail(@PathVariable Integer id, Principal principal) {
        return cartDetailService.deleteCartDetaiilById(id, principal);
    }
    @PostMapping("new")
    public CartDetailVO addToCartDetail(@RequestBody CartDetailVO cartDetailVO, Principal principal) {
        return cartDetailService.save(cartDetailVO, principal);
    }
}
