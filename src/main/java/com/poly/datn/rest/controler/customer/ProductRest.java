package com.poly.datn.rest.controler.customer;

import com.poly.datn.vo.ProductVO;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.common.Constant;
import com.poly.datn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/products")
public class ProductRest {

    @Autowired
    ProductService productService;

    // Mah code
    @GetMapping
    public ResponseEntity<ResponseDTO> getList(@RequestParam("cate") Optional<Integer> cate, @RequestParam("find") Optional<String> find) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getList(cate, find)).build());

    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO> getDetail(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getById(id)).build());
    }
    //End MAH CODE


}
