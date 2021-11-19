package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.OrdersService;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/orders")
public class OrdersRest {

    @Autowired
    OrdersService ordersService;

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<Object>> getOrders(Principal principal, @PathVariable("id") Integer id) throws NullPointerException, SecurityException {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.getByIdAndUserNameAdmin(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());

    }
}
