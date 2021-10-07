package com.poly.datn.service;

import com.poly.datn.VO.CategoryVO;
import com.poly.datn.VO.ProductVO;

import java.security.Principal;
import java.util.List;

public interface CategoryService {
    List<CategoryVO> getCategories();
    CategoryVO createCategory(CategoryVO categoryVO, Principal principal);
    CategoryVO deleteCategory(Integer id, Principal principal);
    CategoryVO updateCategory(CategoryVO categoryVO, Principal principal);
}
