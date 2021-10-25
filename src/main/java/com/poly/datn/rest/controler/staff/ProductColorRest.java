package com.poly.datn.rest.controler.staff;

import com.poly.datn.common.Constant;
import com.poly.datn.service.ColorService;
import com.poly.datn.vo.ColorVO;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/staff/colorproduct")
public class ProductColorRest {
    @Autowired
    ColorService colorService;

    @GetMapping("get")
    public ResponseEntity<ResponseDTO<Object>> getList(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(colorService.getColorProduct(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> updateCartDetail(@RequestBody ColorVO colorVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(colorService.updateColorProduct(colorVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDTO<Object>> deleteCartDetail(@PathVariable Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(colorService.deleteColorProduct(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("new")
    public ResponseEntity<ResponseDTO<Object>> addToCartDetail(@RequestBody ColorVO colorVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(colorService.addColorProduct(colorVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
