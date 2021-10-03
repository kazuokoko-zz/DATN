package com.poly.datn.service.impl;

import com.poly.datn.dao.CartDetailDao;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    CartDetailDao cartDao;

//    @Override
//    public CartDetail getById(Integer id) {
//        CartDetail cartDetail = cartDao.findById(id).get();
//        cartDetail.getId();
//        return cartDetail;
//    }

            @Override
            public List<CartDetail> findCartByUsername(Integer id) {
                return cartDao.findCartDetailsByUsername(id);
                }
}
