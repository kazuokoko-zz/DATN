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

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    ProductDetailsDAO productDetailsDAO;

    @Autowired
    CheckRole checkRole;

    @Override
    public List<ProductDetailsVO> newProductDetail(List<ProductDetailsVO> productDetailsVO, Principal principal) {
//          List<ProductDetails> productDetails = new ArrayList<>();
        if (principal == null) {

        }
        if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {

        List<ProductDetailsVO> productDetailsVOS = new ArrayList<>();
              for(ProductDetailsVO productDetailsVO1 : productDetailsVO) {

              ProductDetails productDetails1 = new ProductDetails();
               ProductDetailsVO productDetailsVO2 = new ProductDetailsVO();
              BeanUtils.copyProperties( productDetailsVO1,productDetails1 );
              productDetails1 = productDetailsDAO.save(productDetails1);
              productDetailsVO2.setId(productDetails1.getId());
              productDetailsVOS.add(productDetailsVO2);

          };
          return productDetailsVOS;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
