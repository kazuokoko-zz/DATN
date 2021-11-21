package com.poly.datn.service.impl;

import com.poly.datn.dao.ProductColorDAO;
import com.poly.datn.entity.ProductColor;
import com.poly.datn.entity.ProductDetails;
import com.poly.datn.service.ProductColorService;
import com.poly.datn.service.ProductService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ProductColorVO;
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
public class ProductColorServiceImpl implements ProductColorService {

    @Autowired
    ProductColorDAO productColorDAO;

    @Autowired
    CheckRole checkRole;

    @Override
    public List<ProductColorVO> newProductColor(Optional<Integer> id, List<ProductColorVO> productColorVOS, Principal principal) {

        if (principal == null) {
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) || !id.isPresent()) {
            return null;
        }
        productColorDAO.deleteAllByProductIdEquals(id.get());

        for (ProductColorVO productColorVO : productColorVOS) {
            ProductColor productColor = new ProductColor();
            BeanUtils.copyProperties(productColorVO, productColor);
            productColor.setProductId(id.get());
            productColorDAO.save(productColor);
        }
        productColorVOS = new ArrayList<>();
        for (ProductColor productColor : productColorDAO.findAllByProductIdEquals(id.get())) {
            ProductColorVO productColorVO = new ProductColorVO();
            BeanUtils.copyProperties(productColor, productColorVO);
            productColorVOS.add(productColorVO);
        }
        return productColorVOS;
    }
}
