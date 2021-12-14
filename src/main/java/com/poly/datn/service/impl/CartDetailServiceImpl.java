package com.poly.datn.service.impl;

import com.poly.datn.common.Constant;
import com.poly.datn.dao.*;
import com.poly.datn.entity.Product;
import com.poly.datn.entity.ProductColor;
import com.poly.datn.entity.ProductDetails;
import com.poly.datn.service.SaleService;
import com.poly.datn.utils.PriceUtils;
import com.poly.datn.vo.CartDetailVO;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    private static final Logger log = LoggerFactory.getLogger(CartDetailServiceImpl.class);

    @Autowired
    CartDetailDAO cartDao;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CartDetailDAO cartDetailDAO;

    @Autowired
    ProductDetailsDAO productDetailsDAO;
    @Autowired
    ProductColorDAO productColorDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    SaleService saleService;

    @Autowired
    PriceUtils priceUtils;
    @Override
    public List<CartDetailVO> findCartByUsername(Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return new ArrayList<>();
        }
        List<CartDetailVO> cartDetailVOS = new ArrayList<>();
        cartDao.getCartDetailsByUsername(principal.getName()).forEach(orders -> {
            CartDetailVO vo = new CartDetailVO();
            BeanUtils.copyProperties(orders, vo);
            Product product = productDAO.getById(vo.getProductId());
            vo.setProductName(product.getName());
            vo.setDiscount(saleService.getCurrentSaleOf(vo.getProductId()));
            vo.setPrice(productDAO.getById(vo.getProductId()).getPrice());
            List<String> photos = new ArrayList<>();
            for (ProductDetails productDetails : productDetailsDAO.findAllByProductIdEquals(product.getId())) {
                if (productDetails.getPropertyName().equalsIgnoreCase("photo")) {
                    for (String photo : productDetails.getPropertyValue().split(",")) {
                        photos.add(photo.trim());
                    }
                }
            }
            if (photos.size() > 0)
                vo.setPhoto(photos.get(0));
            vo.setDiscount(priceUtils.maxDiscountAtPresentOf(vo.getProductId()));
            vo.setPriceBefforSale(vo.getPrice() - vo.getDiscount());
            cartDetailVOS.add(vo);
        });
        return cartDetailVOS;

    }


    @Override
    @Transactional
    public boolean deleteCartDetaiilById(Integer id, Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return false;
        }
        cartDetailDAO.deleteById(id);
        return true;
    }


    @Override
    @Transactional
    public CartDetailVO save(CartDetailVO cartDetailVO, Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            throw new NotImplementedException("Chưa đăng nhập");
        }
        CartDetail cartDetail = new CartDetail();
        Integer productId = cartDetailVO.getProductId();
        if (productId == null){
            throw new NotImplementedException("Chưa chọn sản phẩm");
        }
        Integer colorId = cartDetailVO.getColorId();
        if (colorId == null){
            throw new NotImplementedException("Chưa chọn màu");
        }
        ProductColor productColor = productColorDAO.findByProductIdAndColorId(productId,colorId);
        if(productColor == null){
            throw new NotImplementedException("Sản phẩm không có màu này");
        }
        if(productColor.getQuantity() <= 0){
            throw new NotImplementedException("Màu này đã hết hàng");
        }
        Integer userId = accountDAO.findAccountByUsername(principal.getName()).getId();
        CartDetail cartDetail1 = cartDetailDAO.findOneByProductIdAndUserId(productId, userId, colorId) ;
        if (cartDetail1 == null) {
            BeanUtils.copyProperties(cartDetailVO, cartDetail);
            cartDetail.setUserId(accountDAO.findAccountByUsername(principal.getName()).getId());
            cartDetail = cartDetailDAO.save(cartDetail);
        } else if (cartDetailVO.getQuantity() <= 0) {
            cartDetailDAO.deleteById(cartDetail1.getId());
        } else {
            BeanUtils.copyProperties(cartDetail1, cartDetail);
            cartDetail.setQuantity(cartDetailVO.getQuantity() + cartDetail.getQuantity());
            cartDetailDAO.save(cartDetail);
        }
        BeanUtils.copyProperties(cartDetail, cartDetailVO);
        return cartDetailVO;

    }


}
