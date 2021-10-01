package com.poly.datn.rest.controler.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.datn.entity.Category;
import com.poly.datn.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer/categories")
public class CategoryRest {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
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
