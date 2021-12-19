package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.ProductCategoryService;
import com.poly.datn.service.ProductColorService;
import com.poly.datn.service.ProductDetailService;
import com.poly.datn.service.ProductService;
import com.poly.datn.vo.*;
import com.poly.datn.vo.VoBoSung.ProductDTO.UpdateProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    ProductCategoryService productCategoryService;

    @PutMapping("delete/{id}")
    public ResponseEntity<ResponseDTO<Object>> deleteProduct(@PathVariable("id") Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.delete(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("dontSell/{id}")
    public ResponseEntity<ResponseDTO<Object>> dontSellProduct(@PathVariable("id") Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.dontSell(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("storageEmpty/{id}")
    public ResponseEntity<ResponseDTO<Object>> storageEmpty(@PathVariable("id") Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.storageEmpty(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("comingsoon/{id}")
    public ResponseEntity<ResponseDTO<Object>> comingsoon(@PathVariable("id") Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.comingsoon(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("productReady/{id}")
    public ResponseEntity<ResponseDTO<Object>> productReady(@PathVariable("id") Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.productReady(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("noSell/{id}")
    public ResponseEntity<ResponseDTO<Object>> noSellProduct(@PathVariable("id") Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.noSell(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Object>> getList(@RequestParam("cate") Optional<Integer> cate, @RequestParam("find") Optional<String> find, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getListAdmin(cate, find, principal)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getListDelete")
    public ResponseEntity<ResponseDTO<Object>> getListDelete(@RequestParam("cate") Optional<Integer> cate, @RequestParam("find") Optional<String> find, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getListDeleteAdmin(cate, find, principal)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
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
    public ResponseEntity<ResponseDTO<Object>> newProductColor(@PathVariable("id") Optional<Integer> id, @RequestBody List<ProductColorVO> productColorVOS, @RequestParam("statusProduct") Optional<String> statusProduct, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productColorService.newProductColor(id, productColorVOS, statusProduct, principal)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("newproductcategory/{id}")
    public ResponseEntity<ResponseDTO<Object>> newProductCategory(@PathVariable("id") Optional<Integer> id, @RequestBody List<ProductCategoryVO> productCategoryVOS, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productCategoryService.newProductCategory(id, productCategoryVOS, principal)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> update(@Valid @RequestBody UpdateProductDTO productVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.update(productVO, principal)).code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("selectcate")
    public ResponseEntity<ResponseDTO<Object>> selectCate(@RequestParam("pid") Integer pid, @RequestParam("cid") Integer cid, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.selectCate(pid, cid, principal))
                .code(Constant.RESPONSEDTO_CODE)
                .message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("unselectcate")
    public ResponseEntity<ResponseDTO<Object>> unSelectCate(@RequestParam("pid") Integer pid, @RequestParam("cid") Integer cid, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.unSelectCate(pid, cid, principal))
                .code(Constant.RESPONSEDTO_CODE)
                .message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("blogless")
    public ResponseEntity<ResponseDTO<Object>> getListProductDontHaveBlog(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(productService.getBlogLess(principal))
                .code(Constant.RESPONSEDTO_CODE)
                .message(Constant.RESPONSEDTO_MESS).build());
    }
}
