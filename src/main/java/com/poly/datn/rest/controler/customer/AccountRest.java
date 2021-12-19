package com.poly.datn.rest.controler.customer;

import com.fasterxml.jackson.databind.JsonNode;


import com.poly.datn.common.Constant;
import com.poly.datn.service.AccountService;
import com.poly.datn.validation.common.response.SuccessResponse;
import com.poly.datn.vo.VoBoSung.Account.AccountRegisterVO;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.VoBoSung.Account.ModifyPassworDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
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

    @GetMapping("findAll")
    public ResponseEntity<ResponseDTO<Object>> getAll(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.findAll(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> updateAccount(@RequestBody JsonNode jsonNode, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.updateAccount(jsonNode, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("create")
    public SuccessResponse create(@Valid @RequestBody AccountRegisterVO accountRegisterVO) throws MessagingException, UnsupportedEncodingException {
        accountService.create(accountRegisterVO);
        return new SuccessResponse();
    }

    @PutMapping("resetpass")
    public SuccessResponse updateResetPasswordToken(@RequestParam String email) {
        try {
            accountService.updateResetPasswordToken(email);
            return new SuccessResponse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("updatePass")
    public  ResponseEntity<ResponseDTO<Object>> updatePass(@Valid @RequestBody ModifyPassworDTO modifyPassworDTO, Principal principal1){
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.updatePassword(modifyPassworDTO, principal1))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("checkTokenReset")
    public ResponseEntity<ResponseDTO<Object>> checkTokenResetPass(@RequestParam String token) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.checkToken(token))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("checkEmail")
    public ResponseEntity<ResponseDTO<Object>> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.checkEmail(email))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("checkUsername")
    public ResponseEntity<ResponseDTO<Object>> checkUsername(@RequestParam String username) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.checkUsername(username))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
