package com.poly.datn.rest.controler.customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.datn.VO.AccountVO;
import com.poly.datn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/customer/account")
public class AccountRest {
    @Autowired
    AccountService accountService;

    //Begin code of MA

    @GetMapping
    public AccountVO getAccount(Principal principal) {
        return accountService.findByUsername(principal);
    }

    public AccountVO updateAccount(@RequestBody JsonNode jsonNode, Principal principal) {
        return accountService.updateAccount(jsonNode, principal);
    }

    //End code of MA


}
