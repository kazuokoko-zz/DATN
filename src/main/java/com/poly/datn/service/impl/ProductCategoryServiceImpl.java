package com.poly.datn.service.impl;

import com.poly.datn.vo.ProductCategoryVO;
import com.poly.datn.dao.ProductCategoryDAO;
import com.poly.datn.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryDAO productCategoryDAO;

    @Override
    public List<ProductCategoryVO> getByCategoryId(Integer integer) {
        List<ProductCategoryVO> productCategoryVOS = new ArrayList<>();
        productCategoryDAO.findAllByCategoryIdEquals(integer).forEach(productCategory -> {
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            BeanUtils.copyProperties(productCategory, productCategoryVO);
            productCategoryVOS.add(productCategoryVO);
        });
        return productCategoryVOS;
    }
}
