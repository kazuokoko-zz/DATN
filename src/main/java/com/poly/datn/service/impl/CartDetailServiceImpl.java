package com.poly.datn.service.impl;

import com.poly.datn.common.Constant;
import com.poly.datn.dao.ProductDetailsDAO;
import com.poly.datn.entity.Product;
import com.poly.datn.entity.ProductDetails;
import com.poly.datn.service.SaleService;
import com.poly.datn.vo.CartDetailVO;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.CartDetailDAO;
import com.poly.datn.dao.ProductDAO;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
import com.poly.datn.vo.ProductDetailsVO;
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
    AccountDAO accountDAO;

    @Autowired
    SaleService saleService;

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
            return null;
        }
        CartDetail cartDetail = new CartDetail();
        Integer productId = cartDetailVO.getProductId();
        Integer userId = accountDAO.findAccountByUsername(principal.getName()).getId();
        CartDetail cartDetail1 = cartDetailDAO.findOneByProductIdAndUserId(productId, userId);
        if (cartDetail1 == null) {

            BeanUtils.copyProperties(cartDetailVO, cartDetail);
            cartDetail.setUserId(accountDAO.findAccountByUsername(principal.getName()).getId());
            cartDetail = cartDetailDAO.save(cartDetail);
        } else if (cartDetailVO.getQuantity() <= 0) {
            cartDetailDAO.deleteById(cartDetail1.getId());
            return null;
        } else {
            BeanUtils.copyProperties(cartDetail1, cartDetail);
            cartDetail.setQuantity(cartDetailVO.getQuantity() + cartDetail.getQuantity());
            cartDetailDAO.save(cartDetail);
        }
        BeanUtils.copyProperties(cartDetail, cartDetailVO);
        return cartDetailVO;

    }


}
