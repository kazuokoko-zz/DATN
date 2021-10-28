package com.poly.datn.rest.controler.customer;

import com.poly.datn.common.Constant;


import com.poly.datn.service.WarrantyInvoiceService;


;
import com.poly.datn.vo.CategoryVO;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.WarrantyInvoiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/customer/warranty_invoice")
public class WarrantyInvoiceRest {
    @Autowired
    WarrantyInvoiceService WarrantyInvoiceService;


    @GetMapping("get")
    public ResponseEntity<ResponseDTO<Object>> getList(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(WarrantyInvoiceService.findAll(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> update(@RequestBody WarrantyInvoiceVO warrantyInvoiceVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(WarrantyInvoiceService.updateWarrantyInvoice(warrantyInvoiceVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("new")
    public ResponseEntity<ResponseDTO<Object>> addWarrantyInvoice(@RequestBody WarrantyInvoiceVO warrantyInvoiceVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(WarrantyInvoiceService.save(warrantyInvoiceVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDTO<Object>> deleteWarrantyInvoice(@PathVariable Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(WarrantyInvoiceService.deleteWarrantyInvoiceById(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }



}
