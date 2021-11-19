package com.poly.datn.rest.controler.admin;

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
@RequestMapping("/api/admin/color")
public class ColorAdminRest {
    @Autowired
    ColorService colorService;

    @GetMapping("get")
    public ResponseEntity<ResponseDTO<Object>> getListColor(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(colorService.getColor(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> updateColor(@RequestBody ColorVO colorVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(colorService.updateColor(colorVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDTO<Object>> deleteColor(@PathVariable Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(colorService.deleteColor(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("new")
    public ResponseEntity<ResponseDTO<Object>> addColor(@RequestBody ColorVO colorVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(colorService.addColor(colorVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
