package com.poly.datn.controller;

import com.poly.datn.service.ICategoryTypeService;
import com.poly.datn.vo.CategoryTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private ICategoryTypeService categoryTypeService;

    @GetMapping("/categorytype")
    public List<CategoryTypeVO> readAll() {
        return categoryTypeService.realAll();
    }

    @PostMapping("/categorytype/new")
    public CategoryTypeVO create(@RequestBody CategoryTypeVO voCategoryType) {
        return categoryTypeService.create(voCategoryType);
    }
    @PutMapping("/categorytype/update")
    public CategoryTypeVO update(@RequestBody CategoryTypeVO voCategoryType) {
        return categoryTypeService.update(voCategoryType);
    }
    @DeleteMapping("/categorytype/delete/{id}")
    public CategoryTypeVO delete(@PathVariable Integer id) {
        return categoryTypeService.delete(id);
    }


}
