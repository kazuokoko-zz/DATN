package com.poly.datn.rest.controler.customer;

import com.fasterxml.jackson.databind.JsonNode;


import com.poly.datn.common.Constant;
import com.poly.datn.service.AccountService;
import com.poly.datn.validation.common.response.SuccessResponse;
import com.poly.datn.vo.AccountVO;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ResponseDTO<Object>> create(@RequestBody AccountVO accountVO) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.create(accountVO))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    //End code of MA
//    @PostMapping("resetpass")
//    public ResponseEntity<ResponseDTO<Object>> updateResetPasswordToken(@RequestParam String email){
//        try {
//            return ResponseEntity.ok(ResponseDTO.builder().object(accountService.updateResetPasswordToken(email))
//                    .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
//        } catch (Exception e){
//            throw  new RuntimeException(e);
//        }
//    }
    @PutMapping("resetpass")
    public SuccessResponse updateResetPasswordToken(@RequestParam String email) {
        try {
            accountService.updateResetPasswordToken(email);
            return new SuccessResponse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//    @PostMapping("checkTokenReset")
//    public  SuccessResponse checkTokenResetPass(@RequestParam String token){
//        accountService.checkToken(token);
//        return new SuccessResponse();
//    }
//}

    @PutMapping("checkTokenReset")
    public ResponseEntity<ResponseDTO<Object>> checkTokenResetPass(@RequestParam String token) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.checkToken(token))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
