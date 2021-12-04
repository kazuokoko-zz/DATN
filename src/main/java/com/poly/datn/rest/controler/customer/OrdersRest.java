package com.poly.datn.rest.controler.customer;


import com.poly.datn.vo.OrdersVO;

import com.poly.datn.common.Constant;
import com.poly.datn.service.OrdersService;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDTO<Object>> getList(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.getByUsername(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getall")
    public ResponseEntity<ResponseDTO<Object>> getAll(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.getAll(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<Object>> getOrders(Principal principal, @PathVariable("id") Integer id) throws NullPointerException, SecurityException {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.getByIdAndUserName(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PostMapping("new")
    public ResponseEntity<ResponseDTO<Object>> newOrder(@RequestBody OrdersVO ordersVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.newOrder(ordersVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("confimOrder")
    public ResponseEntity<ResponseDTO<Object>> confimOrder(@RequestParam Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.confimOrder(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("cancerOrder")
    public ResponseEntity<ResponseDTO<Object>> cancerOrder(@RequestParam Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.cancerOrder(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

//    @PostMapping("new")
//public  ResponseEntity<>
}
