package com.poly.datn.service.impl;

import com.poly.datn.VO.CategoryVO;
import com.poly.datn.dao.CategoryDAO;
import com.poly.datn.entity.Category;
import com.poly.datn.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public List<CategoryVO> getCategories() {
        List<CategoryVO> categories = new ArrayList<>();
        categoryDAO.findRootCategories().forEach(category -> {
            CategoryVO categoryVO = new CategoryVO();
            List<CategoryVO> categoryVOS = new ArrayList<>();
            for (Category category1 : categoryDAO.findChildCategories(category.getId())) {
                CategoryVO vo = new CategoryVO();
                BeanUtils.copyProperties(category1, vo);
                categoryVOS.add(vo);
            }
            BeanUtils.copyProperties(category, categoryVO);
            categoryVO.setCategories(categoryVOS);
            categories.add(categoryVO);
        });
        return categories;
    }
}
