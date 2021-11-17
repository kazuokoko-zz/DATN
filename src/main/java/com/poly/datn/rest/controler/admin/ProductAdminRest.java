package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.ProductService;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/products")
public class ProductAdminRest {
    @Autowired
    ProductService productService;

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDTO<Object>> deleteCartDetail(@PathVariable("id") Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.delete(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Object>> getList(@RequestParam("cate") Optional<Integer> cate, @RequestParam("find") Optional<String> find) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getList(cate, find)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<Object>> getDetail(@PathVariable("id") Integer id) throws NullPointerException {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getById(id)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

}
