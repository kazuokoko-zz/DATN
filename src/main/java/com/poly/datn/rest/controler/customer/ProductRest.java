package com.poly.datn.rest.controler.customer;

import com.poly.datn.VO.ProductVO;
import com.poly.datn.VO.ResponseVO;
import com.poly.datn.common.Constant;
import com.poly.datn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/products")
public class ProductRest {

    @Autowired
    ProductService productService;

    // Mah code
    @GetMapping
    public ResponseEntity<ResponseVO> getList(@RequestParam("cate") Optional<Integer> cate, @RequestParam("find") Optional<String> find) {
        return ResponseEntity.ok(ResponseVO.builder()
                .messageCode("200")
                .messageName("Success")
                .data(productService.getList(cate, find)).build());
    }

    @GetMapping("{id}")
    public ProductVO getDetail(@PathVariable("id") Integer id) throws Exception {
        return productService.getById(id);
    }

    //End MAH CODE


}
