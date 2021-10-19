package com.poly.datn.service.impl;

import com.poly.datn.common.Constant;
import com.poly.datn.vo.CategoryVO;
import com.poly.datn.dao.CategoryDAO;
import com.poly.datn.entity.Category;
import com.poly.datn.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

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

    @Override
    public CategoryVO createCategory(CategoryVO categoryVO, Principal principal){
        if(principal == null){
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryVO, category);
        category = categoryDAO.save(category);
        categoryVO.setId(category.getId());
        return  categoryVO;

    }

    @Override
    public CategoryVO deleteCategory(Integer id, Principal principal) {
        if(principal == null){
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        }
        CategoryVO categoryVO = new CategoryVO();
        Optional<Category> optionalCategory = categoryDAO.findById(id);
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            categoryDAO.findChildCategories(id).forEach(categoryes -> {
                categoryes.setStatus(false);
                categoryDAO.save(categoryes);
            });
           category.setStatus(false);
            BeanUtils.copyProperties(category, categoryVO);
            categoryDAO.save(category);
        }
        return categoryVO;
    }

    @Override
    public CategoryVO updateCategory(CategoryVO categoryVO, Principal principal) {
        if(principal == null){
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        }
       Optional<Category> optionalCategory = categoryDAO.findById(categoryVO.getId());
         if(optionalCategory.isPresent()){
             Category entityCategory = new Category();
             BeanUtils.copyProperties(categoryVO, entityCategory);
             categoryDAO.save(entityCategory);
         }
        return  categoryVO;
    }
}
