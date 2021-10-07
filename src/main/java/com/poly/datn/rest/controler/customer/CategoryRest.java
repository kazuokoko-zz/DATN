package com.poly.datn.rest.controler.customer;

import com.poly.datn.VO.CategoryVO;
import com.poly.datn.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/customer/categories")
public class CategoryRest {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<CategoryVO> getCategories() {
        return categoryService.getCategories();
    }


//    @GetMapping
//    public JsonNode getCategories() throws JsonProcessingException {
//        ObjectMapper mapper  = new ObjectMapper();
//        String json = mapper.writeValueAsString(categoryService.getCategories());
//        JsonNode jsonNode = mapper.readTree(json);
//        return jsonNode;
//    }
}
