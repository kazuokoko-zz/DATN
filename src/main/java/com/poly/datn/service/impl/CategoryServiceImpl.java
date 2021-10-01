package com.poly.datn.service.impl;

import com.poly.datn.dao.CategoryDAO;
import com.poly.datn.entity.Category;
import com.poly.datn.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public List<Category> getCategories() {
        List<Category> categories = categoryDAO.findCategories();
        categories.forEach(category -> {
            category.getCategories();
        });
        return categories;
    }
}
