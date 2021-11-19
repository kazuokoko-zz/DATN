package com.poly.datn.rest.controler.admin;


import com.poly.datn.common.Constant;
import com.poly.datn.service.CustomerService;
import com.poly.datn.vo.CommentVO;
import com.poly.datn.vo.CustomerVO;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/customer")
public class CustomerAdminRest {
    @Autowired
    CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO<Object>> getAllCustomer(Principal principal) throws NullPointerException {
        return ResponseEntity.ok(ResponseDTO.builder().object(customerService.getAllCustomer(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO<Object>> getCustomerById(@PathVariable("id") Long id, Principal principal) throws NullPointerException {
        return ResponseEntity.ok(ResponseDTO.builder().object(customerService.getCustomerById(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> updateCustomer(@RequestBody CustomerVO customerVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(customerService.updateCustomer(customerVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
