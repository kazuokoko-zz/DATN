package com.poly.datn.service.impl;

import com.poly.datn.dao.CartDetailDao;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    CartDetailDao cartDao;

//    @Override
//    public CartDetail getById(Integer id) {
//        CartDetail cartDetail = cartDao.findById(id).get();
//        cartDetail.getId();
//        return cartDetail;
//    }

            @Override
            public List<CartDetail> findCartByUsername(String username) {
                return cartDao.getCartDetailsByUsername(username);
                }
}
