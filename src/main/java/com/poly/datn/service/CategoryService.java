package com.poly.datn.service;

import com.poly.datn.vo.CategoryVO;

import java.security.Principal;
import java.util.List;

public interface CategoryService {
    List<CategoryVO> getCategories();
    CategoryVO createCategory(CategoryVO categoryVO, Principal principal);
    CategoryVO deleteCategory(Integer id, Principal principal);
    CategoryVO updateCategory(CategoryVO categoryVO, Principal principal);
}
