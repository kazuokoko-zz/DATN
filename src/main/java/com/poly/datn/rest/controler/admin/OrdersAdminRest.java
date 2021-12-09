package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.OrdersService;
import com.poly.datn.vo.OrdersVO;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/orders")
public class OrdersAdminRest {

    @Autowired
    OrdersService ordersService;

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<Object>> getOrders(Principal principal, @PathVariable("id") Integer id) throws NullPointerException, SecurityException {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.getByIdAndUserNameAdmin(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO<Object>> getList(Principal principal,
                                                       @RequestParam("id") Optional<Integer> id,
                                                       @RequestParam("email") Optional<String> email,
                                                       @RequestParam("name") Optional<String> name,
                                                       @RequestParam("phone") Optional<String> phone) throws NullPointerException, SecurityException {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.getList(principal, id, email, name, phone))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("new")
    public ResponseEntity<ResponseDTO<Object>> newOrder(@RequestBody OrdersVO ordersVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.newOrderAdmin(ordersVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> updateOrder(@RequestParam("id") Optional<Integer> id, @RequestParam("status") Optional<String> status, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.updateOrderAdmin(id, status, principal))
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
}
