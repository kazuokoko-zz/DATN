package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.AccountService;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.VoBoSung.Account.NewAccountUserAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
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
    @PostMapping("newAccount")
    public ResponseEntity<ResponseDTO<Object>> newAccount(@Valid @RequestBody NewAccountUserAdmin newAccountUserAdmin, Principal principal) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.createAdmin(newAccountUserAdmin, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("lockAccount")
    public ResponseEntity<ResponseDTO<Object>> lockAccount(@RequestParam Integer id, Principal principal){
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.deleteAccount(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
        @PutMapping("openAccount")
    public ResponseEntity<ResponseDTO<Object>> openAccount(@RequestParam Integer id, Principal principal){
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.openAccount(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
