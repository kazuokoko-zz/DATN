package com.poly.datn.service.impl;

import com.poly.datn.dao.CartDetailDao;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {
                @Autowired
                CartDetailDao cartDao;
   @Autowired
   CartDetailService cartDetailService;
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

    @Override
    public CartDetail getCartDetailById(CartDetail cartDetail) {
        Optional<CartDetail> optionalCartDetail = cartDao.findById(cartDetail.getId());
        if(optionalCartDetail.isPresent()){
            CartDetail entity = optionalCartDetail.get();
            BeanUtils.copyProperties(cartDetail, entity);
            cartDao.save(entity);
        }
        return  cartDetail;
    }
}
