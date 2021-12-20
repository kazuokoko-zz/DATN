package com.poly.datn.service.impl;

import com.poly.datn.dao.ProductCategoryDAO;
import com.poly.datn.entity.ProductCategory;
import com.poly.datn.service.ProductCategoryService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ProductCategoryVO;
import com.poly.datn.vo.VoBoSung.ProductDTO.UpdateProductCategoryDTO;
import org.apache.commons.lang.NotImplementedException;
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
            throw new NotImplementedException("Chưa đăng nhập");
        }
        if (!id.isPresent() || !(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
        }
        if (productCategoryVOS == null) {
            throw new NotImplementedException("Chưa thêm chi tiết sản phẩm");
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
    public void updateProductCategory(Integer id, List<UpdateProductCategoryDTO> productCategoryVOS) {

        productCategoryDAO.deleteAllByProductIdEquals(id);
        for (UpdateProductCategoryDTO productCategoryVO : productCategoryVOS) {
            ProductCategory productCategory = new ProductCategory();
            BeanUtils.copyProperties(productCategoryVO, productCategory);
            productCategory.setProductId(id);
            productCategoryDAO.save(productCategory);
        }
        productCategoryVOS = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryDAO.findAllByProductIdEquals(id)) {
            UpdateProductCategoryDTO productCategoryVO = new UpdateProductCategoryDTO();
            BeanUtils.copyProperties(productCategory, productCategoryVO);
            productCategoryVOS.add(productCategoryVO);
        }
    }


}
