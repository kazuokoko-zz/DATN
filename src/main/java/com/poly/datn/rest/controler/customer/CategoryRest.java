package com.poly.datn.rest.controler.customer;

import com.poly.datn.VO.CategoryVO;
import com.poly.datn.VO.ResponseDTO;
import com.poly.datn.common.Constant;
import com.poly.datn.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/categories")
public class CategoryRest {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getCategories() {
        return ResponseEntity.ok(ResponseDTO.builder().object(categoryService.getCategories()).build());
    }


//    @GetMapping
//    public JsonNode getCategories() throws JsonProcessingException {
//        ObjectMapper mapper  = new ObjectMapper();
//        String json = mapper.writeValueAsString(categoryService.getCategories());
//        JsonNode jsonNode = mapper.readTree(json);
//        return jsonNode;
//    }
}
