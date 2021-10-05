package com.poly.datn.rest.controler.customer;

import com.poly.datn.VO.ProductVO;
import com.poly.datn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer/products")
public class ProductRest {

    @Autowired
    ProductService productService;

    // Mah code
    @GetMapping
    public List<ProductVO> getList(@RequestParam("cate") Optional<Integer> cate, @RequestParam("find") Optional<String> find) {
        return productService.getList(cate, find);
    }

    @GetMapping("{id}")
    public ProductVO getDetail(@PathVariable("id") Integer id) throws Exception {
        return productService.getById(id);
    }

    //End MAH CODE


}
