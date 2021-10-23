package com.poly.datn.rest.controler.admin;


import com.poly.datn.common.Constant;
import com.poly.datn.service.AccountRoleService;
import com.poly.datn.vo.AccountRoleVO;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/authorities")
public class AccountRoleAdminRest {
    @Autowired
    AccountRoleService accountRoleService;

    @PostMapping("/grant")
    public ResponseEntity<ResponseDTO<Object>> grant(@RequestBody AccountRoleVO accountRoleVO, Principal principal){
        return ResponseEntity.ok(ResponseDTO.builder().object(accountRoleService.grantRole(accountRoleVO,principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

}
