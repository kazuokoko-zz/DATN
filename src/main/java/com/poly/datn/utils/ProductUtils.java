package com.poly.datn.utils;

import com.poly.datn.dao.ColorDAO;
import com.poly.datn.dao.ProductColorDAO;
import com.poly.datn.dao.ProductDetailsDAO;
import com.poly.datn.entity.Color;
import com.poly.datn.entity.Product;
import com.poly.datn.entity.ProductColor;
import com.poly.datn.entity.ProductDetails;
import com.poly.datn.vo.ColorVO;
import com.poly.datn.vo.ProductColorVO;
import com.poly.datn.vo.ProductDetailsVO;
import com.poly.datn.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductUtils {
    @Autowired
    ProductColorDAO productColorDAO;
    @Autowired
    ProductDetailsDAO productDetailsDAO;
    @Autowired
    PriceUtils priceUtils;
    @Autowired
    ColorDAO colorDAO;

    public ProductVO convertToVO(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        List<ProductColorVO> productColorVOS = new ArrayList<>();
        for (ProductColor productColor : productColorDAO.findAllByProductIdEquals(productVO.getId())) {
            ProductColorVO productColorVO = new ProductColorVO();
            if (productColor.getQuantity() <= 0) {
                continue;
            }
            BeanUtils.copyProperties(productColor, productColorVO);
            Color color = colorDAO.findColorById(productColorVO.getColorId());
            ColorVO colorVO = new ColorVO();
            BeanUtils.copyProperties(color, colorVO);
            productColorVO.setColor(colorVO);
            productColorVOS.add(productColorVO);
        }
        productVO.setProductColors(productColorVOS);
        List<ProductDetailsVO> productDetailsVOS = new ArrayList<>();
        List<String> photos = new ArrayList<>();
        for (ProductDetails productDetails : productDetailsDAO.findAllByProductIdEquals(productVO.getId())) {
            ProductDetailsVO productDetailsVO = new ProductDetailsVO();
            if (productDetails.getPropertyName().equalsIgnoreCase("photo")) {
                for (String photo : productDetails.getPropertyValue().split(",")) {
                    photos.add(photo.trim());
                }
            } else {
                BeanUtils.copyProperties(productDetails, productDetailsVO);
                productDetailsVOS.add(productDetailsVO);
            }
        }
        productVO.setProductDetails(productDetailsVOS);
        productVO.setPhotos(photos);
        productVO.setDiscount(priceUtils.maxDiscountAtPresentOf(productVO.getId()));

        return productVO;
    }
}
