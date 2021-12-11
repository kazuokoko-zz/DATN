package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.AccountService;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/account")
public class AccountAdminRest {

    @Autowired
    AccountService accountService;

    @GetMapping("find")
    public ResponseEntity<ResponseDTO<Object>> getAll(@RequestParam("id") Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.findByUsernameAdmin(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
