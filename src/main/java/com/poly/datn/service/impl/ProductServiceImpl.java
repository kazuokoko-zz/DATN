package com.poly.datn.service.impl;

import com.poly.datn.Utils.StringFind;
import com.poly.datn.dao.ProductCategoryDAO;
import com.poly.datn.dao.ProductDAO;
import com.poly.datn.entity.Product;
import com.poly.datn.entity.ProductCategory;
import com.poly.datn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductCategoryDAO productCategoryDAO;

    @Override
    public List<Product> getList(Optional<Integer> cate, Optional<String> find) {
        if (cate.isPresent() && find.isPresent()) {
            List<Product> products = getListByCate(cate.get());
            products = StringFind.getMatch(products, find.get());
            return products;
        } else if (cate.isPresent()) {
            return getListByCate(cate.get());
        } else if (find.isPresent()) {
            List<Product> products = productDAO.findAll();
            products = StringFind.getMatch(products, find.get());
            return products;
        } else {
            return productDAO.findAll();
        }
    }

    @Override
    public List<Product> getListByCate(Integer cateId) {
        List<Product> products = new ArrayList<>();
        List<ProductCategory> productCategories = productCategoryDAO.findAllByCategoryIdEquals(cateId);
        for (ProductCategory productCategory : productCategories) {
            Optional<Product> optionalProduct = productDAO.findById(productCategory.getProductId());
            if (optionalProduct.isPresent()) {
                products.add(optionalProduct.get());
            }
        }
        return products;
    }

}
