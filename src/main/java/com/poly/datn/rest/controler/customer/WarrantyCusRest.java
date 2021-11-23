package com.poly.datn.rest.controler.customer;

import com.poly.datn.common.Constant;
import com.poly.datn.service.WarrantyService;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.WarrantyVO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/warranty")
public class WarrantyCusRest {
    @Autowired
    WarrantyService warrantyService;

    @GetMapping("get")
    public ResponseEntity<ResponseDTO<Object>> getListWarranty(Principal principal) throws NotFoundException {
        return ResponseEntity.ok(ResponseDTO.builder().object(warrantyService.getByUsername(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }


}
