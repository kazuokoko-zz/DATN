package com.poly.datn.rest.controler.customer;

import com.poly.datn.common.Constant;
import com.poly.datn.service.CategoryService;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/categories")
public class CategoryRest {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponseDTO<Object>> getCategories(@RequestParam("active") Optional<Boolean> active) {
        return ResponseEntity.ok(ResponseDTO.builder().object(categoryService.getCategories(active))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }


}
