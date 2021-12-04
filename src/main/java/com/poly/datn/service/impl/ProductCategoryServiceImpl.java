package com.poly.datn.service.impl;

import com.poly.datn.dao.ProductCategoryDAO;
import com.poly.datn.entity.ProductCategory;
import com.poly.datn.service.ProductCategoryService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ProductCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryDAO productCategoryDAO;
    @Autowired
    CheckRole checkRole;

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

    @Override
    public List<ProductCategoryVO> newProductCategory(Optional<Integer> id, List<ProductCategoryVO> productCategoryVOS, Principal principal) {
        if (principal == null) {
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) || !id.isPresent()) {
            return null;
        }
        productCategoryDAO.deleteAllByProductIdEquals(id.get());

        for (ProductCategoryVO productCategoryVO : productCategoryVOS) {
            ProductCategory productCategory = new ProductCategory();
            BeanUtils.copyProperties(productCategoryVO, productCategory);
            productCategory.setProductId(id.get());
            productCategoryDAO.save(productCategory);
        }
        productCategoryVOS = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryDAO.findAllByProductIdEquals(id.get())) {
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            BeanUtils.copyProperties(productCategory, productCategoryVO);
            productCategoryVOS.add(productCategoryVO);
        }
        return productCategoryVOS;
    }


}
