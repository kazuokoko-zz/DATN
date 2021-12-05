package com.poly.datn.rest.controler.admin;


import com.poly.datn.common.Constant;
import com.poly.datn.service.SaleService;
import com.poly.datn.vo.ProductSaleVO;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.SaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/sale")
public class SaleAdminRest {

    @Autowired
    SaleService saleService;

    @GetMapping("get")
    public ResponseEntity<ResponseDTO<Object>> getAllSale(Principal principal) {
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
    @PostMapping("newSale")
    public ResponseEntity<ResponseDTO<Object>> newSale(@RequestBody SaleVO saleVO,Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.newSale(saleVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PostMapping("updateSale")
    public ResponseEntity<ResponseDTO<Object>> updateSale(@RequestBody SaleVO saleVO,Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.updateSale(saleVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PostMapping("newProductSale")
    public ResponseEntity<ResponseDTO<Object>> newProductSale(@RequestBody ProductSaleVO productSaleVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.newProductSale(productSaleVO ,
                        principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PostMapping("updateProductSale")
    public ResponseEntity<ResponseDTO<Object>> updateProductSale(@RequestBody ProductSaleVO productSaleVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.updateProductSale(productSaleVO ,
                        principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PostMapping("stopProductSale")
    public ResponseEntity<ResponseDTO<Object>> stopProductSale(@RequestParam Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.deleteProductSale(id ,
                        principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PutMapping("stopSale")
    public ResponseEntity<ResponseDTO<Object>> stopSale(@RequestParam Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(saleService.stopSale(id ,
                        principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

}
