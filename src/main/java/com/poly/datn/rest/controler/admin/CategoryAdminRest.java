package com.poly.datn.rest.controler.admin;

import com.poly.datn.vo.CategoryVO;
import com.poly.datn.common.Constant;
import com.poly.datn.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/categories")
public class CategoryAdminRest {
    @Autowired
    CategoryService categoryService;
    @PostMapping("newcategory")
    public CategoryVO newCategoryVO(@RequestBody CategoryVO categoryVO, Principal principal){
        return  categoryService.createCategory(categoryVO, principal);
    }
    @PutMapping ("update")
    public CategoryVO updateCategoryVO(@RequestBody CategoryVO categoryVO, Principal principal){
         return categoryService.updateCategory(categoryVO, principal);
    }
    @DeleteMapping ("delete/{id}")
    public CategoryVO deleteCategoryVO(@PathVariable Integer id, Principal principal){
          return  categoryService.deleteCategory(id, principal);
    }
}
