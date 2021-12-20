package com.poly.datn.rest.controler.customer;


import com.poly.datn.common.Constant;
import com.poly.datn.service.OrdersService;
import com.poly.datn.vo.NewOrdersVO;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.VoBoSung.NoteOrderManagementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public ResponseEntity<ResponseDTO<Object>> newOrder(@Validated @RequestBody NewOrdersVO ordersVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.newOrder(ordersVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("cancerOrderUser")
    public ResponseEntity<ResponseDTO<Object>> cancerOrderUser(@RequestBody NoteOrderManagementVo noteOrderManagementVo,Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.cancerOrderUser(noteOrderManagementVo,id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("requestCancerOrderUser")
    public ResponseEntity<ResponseDTO<Object>> requestCancerOrderUser(@RequestBody  NoteOrderManagementVo noteOrderManagementVo,Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.requestCancerOrderUser(noteOrderManagementVo,id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("unCancerOrderUser")
    public ResponseEntity<ResponseDTO<Object>> unCancerOrderUser(@RequestBody  NoteOrderManagementVo noteOrderManagementVo,Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.unCancerOrderUser(noteOrderManagementVo,id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("unConfimReturnsUser")
    public ResponseEntity<ResponseDTO<Object>> unConfimReturnsUser(@RequestBody  NoteOrderManagementVo noteOrderManagementVo,Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.unConfimReturnsUser(noteOrderManagementVo,id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("confimReturnsUser")
    public ResponseEntity<ResponseDTO<Object>> confimReturnsUser(@RequestBody  NoteOrderManagementVo noteOrderManagementVo,Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.confimReturnsUser(noteOrderManagementVo,id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }


//    @PostMapping("new")
//public  ResponseEntity<>
}
