package com.poly.datn.service.impl;

import com.poly.datn.dao.ProductDetailsDAO;
import com.poly.datn.entity.ProductDetails;
import com.poly.datn.service.ProductDetailService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ProductDetailsVO;
import com.poly.datn.vo.VoBoSung.ProductDTO.UpdateProductDetailDTO;
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
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    ProductDetailsDAO productDetailsDAO;

    @Autowired
    CheckRole checkRole;

    @Override
    public List<ProductDetailsVO> newProductDetail(Optional<Integer> id, List<ProductDetailsVO> productDetailsVOS, Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        if (!id.isPresent() || !(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
        }
        if (productDetailsVOS == null) {
            throw new NotImplementedException("Chưa thêm chi tiết sản phẩm");
        }
        productDetailsDAO.deleteAllByProductIdEquals(id.get());
        List<String> photos = new ArrayList<>();
        for (ProductDetailsVO productDetailsVO : productDetailsVOS) {
            if (productDetailsVO.getPropertyName().equalsIgnoreCase("photo")) {
                photos.add(productDetailsVO.getPropertyValue());
                continue;
            }
            ProductDetails productDetails = new ProductDetails();
            BeanUtils.copyProperties(productDetailsVO, productDetails);
            productDetails.setProductId(id.get());
            productDetailsDAO.save(productDetails);
        }
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(id.get());
        productDetails.setPropertyName("photo");
        productDetails.setPropertyValue(String.join(",", photos));
        productDetailsDAO.save(productDetails);
        productDetailsVOS = new ArrayList<>();

        for (ProductDetails pd : productDetailsDAO.findAllByProductIdEquals(id.get())) {
            ProductDetailsVO productDetailsVO = new ProductDetailsVO();
            BeanUtils.copyProperties(pd, productDetailsVO);
            productDetailsVOS.add(productDetailsVO);
        }
        return productDetailsVOS;

    }

    public void updateProductDetail(Integer id, List<UpdateProductDetailDTO> productDetailsVOS) {
        productDetailsDAO.deleteAllByProductIdEquals(id);
        List<String> photos = new ArrayList<>();
        for (UpdateProductDetailDTO productDetailsVO : productDetailsVOS) {
            if (productDetailsVO.getPropertyName().equalsIgnoreCase("photo")) {
                photos.add(productDetailsVO.getPropertyValue());
                continue;
            }
            ProductDetails productDetails = new ProductDetails();
            BeanUtils.copyProperties(productDetailsVO, productDetails);
            productDetails.setProductId(id);
            productDetails.setId(null);
            productDetailsDAO.save(productDetails);
        }
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(id);
        productDetails.setPropertyName("photo");
        productDetails.setPropertyValue(String.join(",", photos));
        productDetailsDAO.save(productDetails);
//        productDetailsVOS = new ArrayList<>();
//
//        for (ProductDetails pd : productDetailsDAO.findAllByProductIdEquals(id)) {
//            UpdateProductDetailDTO productDetailsVO = new UpdateProductDetailDTO();
//            BeanUtils.copyProperties(pd, productDetailsVO);
//            productDetailsVOS.add(productDetailsVO);
//        }
    }
}
