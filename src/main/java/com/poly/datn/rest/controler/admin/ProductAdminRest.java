package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.ProductColorService;
import com.poly.datn.service.ProductDetailService;
import com.poly.datn.service.ProductService;
import com.poly.datn.vo.ProductColorVO;
import com.poly.datn.vo.ProductDetailsVO;
import com.poly.datn.vo.ProductVO;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/products")
public class ProductAdminRest {
    @Autowired
    ProductService productService;

    @Autowired
    ProductColorService productColorService;

    @Autowired
    ProductDetailService productDetailService;

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

    @PostMapping("newproduct")
    public ResponseEntity<ResponseDTO<Object>> newProduct(@RequestBody ProductVO productVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.newProduct(productVO, principal)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("newproductdetail/{id}")
    public ResponseEntity<ResponseDTO<Object>> newProductDetail(@PathVariable("id") Optional<Integer> id, @RequestBody List<ProductDetailsVO> productDetailsVOS, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productDetailService.newProductDetail(id, productDetailsVOS, principal)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }


    @PostMapping("newproductcolor/{id}")
    public ResponseEntity<ResponseDTO<Object>> newProductColor(@PathVariable("id") Optional<Integer> id, @RequestBody List<ProductColorVO> productColorVOS, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productColorService.newProductColor(id, productColorVOS, principal)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> update(@RequestBody ProductVO productVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.update(productVO, principal)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
