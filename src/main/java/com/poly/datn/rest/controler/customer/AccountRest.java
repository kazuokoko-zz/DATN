package com.poly.datn.rest.controler.customer;

import com.fasterxml.jackson.databind.JsonNode;

import com.poly.datn.vo.AccountVO;

import com.poly.datn.common.Constant;
import com.poly.datn.service.AccountService;
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
    public ResponseEntity<ResponseDTO> getAccount(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.findByUsername(principal)).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO> updateAccount(@RequestBody JsonNode jsonNode, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.updateAccount(jsonNode, principal)).build());
    }

    //End code of MA


}
