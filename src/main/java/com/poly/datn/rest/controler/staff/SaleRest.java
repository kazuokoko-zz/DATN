package com.poly.datn.rest.controler.staff;


import com.poly.datn.common.Constant;
import com.poly.datn.service.SaleService;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/staff/sale")
public class SaleRest {

    @Autowired
    SaleService saleService;

    @GetMapping("get")
    public ResponseEntity<ResponseDTO<Object>> getAllSake(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.getAll(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("getSaleNow")
    public ResponseEntity<ResponseDTO<Object>> getSaleNow(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.getSaleNow(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("getSaleAboutStart")
    public ResponseEntity<ResponseDTO<Object>> getSaleAboutStart(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.getSaleAboutStart(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("getSellEnd")
    public ResponseEntity<ResponseDTO<Object>> getSellEnd(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.getSellEnd(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
