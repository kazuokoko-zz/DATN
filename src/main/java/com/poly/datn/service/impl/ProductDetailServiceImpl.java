package com.poly.datn.service.impl;

import com.poly.datn.dao.ProductDetailsDAO;
import com.poly.datn.entity.ProductDetails;
import com.poly.datn.service.ProductDetailService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ProductDetailsVO;
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
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    ProductDetailsDAO productDetailsDAO;

    @Autowired
    CheckRole checkRole;

    @Override
    public List<ProductDetailsVO> newProductDetail(Optional<Integer> id, List<ProductDetailsVO> productDetailsVOS, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!id.isPresent() || !(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        productDetailsDAO.deleteAllByProductId(id.get());
        for (ProductDetailsVO productDetailsVO : productDetailsVOS) {
            ProductDetails productDetails = new ProductDetails();
            BeanUtils.copyProperties(productDetailsVO, productDetails);
            productDetails.setProductId(id.get());
            productDetailsDAO.save(productDetails);
        }
        productDetailsVOS = new ArrayList<>();

        for (ProductDetails productDetails : productDetailsDAO.findAllByProductId(id.get())) {
            ProductDetailsVO productDetailsVO = new ProductDetailsVO();
            BeanUtils.copyProperties(productDetails, productDetailsVO);
            productDetailsVOS.add(productDetailsVO);
        }
        return productDetailsVOS;

    }
}
