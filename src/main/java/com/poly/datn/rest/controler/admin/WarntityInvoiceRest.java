package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.WarrantyInvoiceService;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.WarrantyInvoiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/invoice")
public class WarntityInvoiceRest {
    @Autowired
    WarrantyInvoiceService warrantyInvoiceService;

    @PostMapping("new")
    public ResponseEntity<ResponseDTO<Object>> create(@RequestBody WarrantyInvoiceVO invoiceVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(warrantyInvoiceService.create(invoiceVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<Object>> getById(@RequestParam("id") Optional<Integer> id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(warrantyInvoiceService.getById(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
