package com.poly.datn.service.impl;

import com.poly.datn.Utils.StringFind;
import com.poly.datn.VO.ProductColorVO;
import com.poly.datn.VO.ProductDetailsVO;
import com.poly.datn.VO.ProductVO;
import com.poly.datn.dao.ProductCategoryDAO;
import com.poly.datn.dao.ProductColorDAO;
import com.poly.datn.dao.ProductDAO;
import com.poly.datn.dao.ProductDetailsDAO;
import com.poly.datn.entity.Product;
import com.poly.datn.entity.ProductCategory;
import com.poly.datn.service.ProductService;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    ProductColorDAO productColorDAO;

    @Autowired
    ProductDetailsDAO productDetailsDAO;

    // Begin code of MA

    @Override
    public List<ProductVO> getList(Optional<Integer> cate, Optional<String> find) {
        List<Product> products;
        if (cate.isPresent() && find.isPresent()) {
            products = getListByCate(cate.get());
            products = StringFind.getMatch(products, find.get());

        } else if (cate.isPresent()) {
            products = getListByCate(cate.get());
        } else if (find.isPresent()) {
            products = productDAO.findAll();
            products = StringFind.getMatch(products, find.get());

        } else {
            products = productDAO.findAll();
        }

        List<ProductVO> productVOS = new ArrayList<>();
        products.forEach(product -> {
            ProductVO productVO = convertToVO(product);
            productVOS.add(productVO);
        });

        return productVOS;
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

    @Override
    public ProductVO getById(Integer id) throws Exception {
        Product product = productDAO.findById(id).orElseThrow(() -> new Exception("Product not found with id: " + String.valueOf(id)));
        return convertToVO(product);
    }

    private ProductVO convertToVO(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        List<ProductColorVO> productColorVOS = new ArrayList<>();
        productColorDAO.getByProductId(productVO.getId()).forEach(productColor -> {
            ProductColorVO productColorVO = new ProductColorVO();
            BeanUtils.copyProperties(productColor, productColorVO);
            productColorVOS.add(productColorVO);
        });
        productVO.setProductColors(productColorVOS);
        List<ProductDetailsVO> productDetailsVOS = new ArrayList<>();
        productDetailsDAO.getByProductId(productVO.getId()).forEach(productDetails -> {
            ProductDetailsVO productDetailsVO = new ProductDetailsVO();
            BeanUtils.copyProperties(productDetails, productDetailsVO);
            productDetailsVOS.add(productDetailsVO);
        });
        productVO.setProductDetails(productDetailsVOS);
        return productVO;
    }


    // End code of MA

}
