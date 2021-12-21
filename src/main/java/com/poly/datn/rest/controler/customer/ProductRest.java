package com.poly.datn.rest.controler.customer;

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
    public ResponseEntity<ResponseDTO<Object>> getList(@RequestParam("cate") Optional<Integer> cate, @RequestParam("find") Optional<String> find) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getList(cate, find)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("trending")
    public ResponseEntity<ResponseDTO<Object>> getTrending() {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getTrending()).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("mostnew")
    public ResponseEntity<ResponseDTO<Object>> getMostNew() {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getMostNew()).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("discount")
    public ResponseEntity<ResponseDTO<Object>> getDiscount() {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getDiscount()).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("/price")
    public ResponseEntity<ResponseDTO<Object>> getByPrice(@RequestParam("start") Optional<Long> start, @RequestParam("end") Optional<Long> end) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getByPrice(start, end)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<Object>> getDetail(@PathVariable("id") Integer id) throws NullPointerException {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getById(id)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

//    @GetMapping("")
    //End MAH CODE
}
