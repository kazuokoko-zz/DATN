package com.poly.datn.rest.controler.customer;

import com.poly.datn.VO.CartDetailVO;
import com.poly.datn.VO.ResponseDTO;
import com.poly.datn.common.Constant;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/cart")
public class CartDetailsRest {

    @Autowired
    CartDetailService cartDetailService;

    @GetMapping("get")
    public ResponseEntity<ResponseDTO> getList(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(cartDetailService.findCartByUsername(principal)).build()) ;
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO> updateCartDetail(@RequestBody CartDetailVO cartDetailVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(cartDetailService.save(cartDetailVO, principal)).build());
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDTO> deleteCartDetail(@PathVariable Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(cartDetailService.deleteCartDetaiilById(id, principal)).build());
    }
    @PostMapping("new")
    public ResponseEntity<ResponseDTO> addToCartDetail(@RequestBody CartDetailVO cartDetailVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(cartDetailService.save(cartDetailVO, principal)).build());
    }
}
