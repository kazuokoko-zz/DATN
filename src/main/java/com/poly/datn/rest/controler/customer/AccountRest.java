package com.poly.datn.rest.controler.customer;

import com.fasterxml.jackson.databind.JsonNode;


import com.poly.datn.common.Constant;
import com.poly.datn.service.AccountService;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/account")
public class AccountRest {
    @Autowired
    AccountService accountService;

    //Begin code of MA

    @GetMapping
    public ResponseEntity<ResponseDTO<Object>> getAccount(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.findByUsername(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> updateAccount(@RequestBody JsonNode jsonNode, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.updateAccount(jsonNode, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    //End code of MA


}
