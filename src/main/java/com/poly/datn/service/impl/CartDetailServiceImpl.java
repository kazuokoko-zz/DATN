package com.poly.datn.service.impl;

import com.poly.datn.VO.CartDetailVO;
import com.poly.datn.dao.CartDetailDAO;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    CartDetailDAO cartDao;

//    @Override
//    public CartDetail getById(Integer id) {
//        CartDetail cartDetail = cartDao.findById(id).get();
//        cartDetail.getId();
//        return cartDetail;
//    }

    @Override
    public List<CartDetailVO> findCartByUsername(String username) {
        List<CartDetailVO> cartDetailVOS = new ArrayList<>();
        for (CartDetail cartDetail : cartDao.getCartDetailsByUsername(username)) {
            CartDetailVO cartDetailVO = new CartDetailVO();
            BeanUtils.copyProperties(cartDetail, cartDetailVO);
            cartDetailVOS.add(cartDetailVO);
        }
        return cartDetailVOS;
    }
}
