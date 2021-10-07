package com.poly.datn.rest.controler.customer;

import com.poly.datn.VO.AccountVO;
import com.poly.datn.VO.CartDetailVO;
import com.poly.datn.VO.ProductVO;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.CartDetailDAO;
import com.poly.datn.dao.ProductDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.entity.Product;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
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
        return cartDetailService.getCartDetailById(cartDetailVO, principal);
    }
    @DeleteMapping("delete/{id}")
    public CartDetailVO deleteCartDetail(@PathVariable Integer id, Principal principal) {
        return cartDetailService.deleteCartDetaiilById(id, principal);
    }
    @PostMapping("{idProduct}/addtocart/username")
    public CartDetailVO addToCartDetail(@PathVariable Integer idProduct, Principal principal) {
            return cartDetailService.newCartDetaiilByUsername(idProduct, principal);
    }
}
