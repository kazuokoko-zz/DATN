package com.poly.datn.service.impl;

import com.poly.datn.dao.ProductCategoryDAO;
import com.poly.datn.entity.ProductCategory;
import com.poly.datn.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryDAO productCategoryDAO;

    @Override
    public List<ProductCategory> getByCategoryId(Integer integer) {
        return productCategoryDAO.findAllByCategoryIdEquals(integer);
    }
}
