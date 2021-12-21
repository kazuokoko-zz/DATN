package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.WarrantyService;
import com.poly.datn.validation.common.response.SuccessResponse;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.WarrantyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/warranty")
public class WarrantyRest {
    @Autowired
    WarrantyService warrantyService;



    @GetMapping("get")
    public ResponseEntity<ResponseDTO<Object>> getListWarranty(@RequestParam(required = false) Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(warrantyService.getAll(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("get/{id}")
    public ResponseEntity<ResponseDTO<Object>> getAllById(@PathVariable Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(warrantyService.getAllById(id,principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("new")
    public SuccessResponse newWarranty(@Valid @RequestBody WarrantyVO warrantyVO, Principal principal) {
        warrantyService.newWarranty(warrantyVO, principal);
        return new SuccessResponse();
    }

}
