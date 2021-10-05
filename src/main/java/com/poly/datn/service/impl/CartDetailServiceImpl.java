package com.poly.datn.service.impl;

import com.poly.datn.VO.CartDetailVO;
import com.poly.datn.VO.OrdersVO;
import com.poly.datn.dao.CartDetailDAO;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
CartDetailDAO cartDao;
    @Autowired
    CartDetailService cartDetailService;
//    @Override
//    public CartDetail getById(Integer id) {
//        CartDetail cartDetail = cartDao.findById(id).get();
//        cartDetail.getId();
//        return cartDetail;
//    }

    @Override
    public List<CartDetailVO> findCartByUsername(String username) {
        List<CartDetailVO> cartDetailVOS = new ArrayList<>();
        cartDao.getCartDetailsByUsername(username).forEach(orders -> {
            CartDetailVO vo = new CartDetailVO();
            BeanUtils.copyProperties(orders, vo);
            cartDetailVOS.add(vo);
        });
        return cartDetailVOS;
//
//        List<CartDetailVO> voList = new ArrayList<CartDetailVO>();
//        List<CartDetail> optional = cartDao.getCartDetailsByUsername(username);
//        for (CartDetail entity : optional) {
////            cartDao.getCartDetailsByUsername(username).forEach(cartDetail -> {
//                CartDetailVO vo = new CartDetailVO();
//                BeanUtils.copyProperties(entity, vo);
//                voList.add(vo);
////            });
//        }
//        return voList;
    }

    @Override
    public CartDetailVO getCartDetailById(CartDetailVO cartDetailVO) {
        Optional<CartDetail> optionalCartDetail = cartDao.findById(cartDetailVO.getId());
        if(optionalCartDetail.isPresent()){
            Integer checkquantity = cartDetailVO.getQuantity();
            if(checkquantity >0) {
                CartDetail entity = optionalCartDetail.get();
                BeanUtils.copyProperties(cartDetailVO, entity);
                cartDao.save(entity);
            } else {
                CartDetail entity = optionalCartDetail.get();
                BeanUtils.copyProperties(cartDetailVO, entity);
                cartDao.delete(entity);
            }
        }
        return  cartDetailVO;
    }

    @Override
    public CartDetailVO deleteCartDetaiilById(Integer id) {
           CartDetailVO cartDetailVO = new CartDetailVO();
           Optional<CartDetail> optionalCartDetail = cartDao.findById(id);
           if(optionalCartDetail.isPresent()){
               CartDetail entity = optionalCartDetail.get();
               BeanUtils.copyProperties(entity, cartDetailVO);
               cartDao.delete(entity);
           }
        return  cartDetailVO;
    }

    @Override
    public CartDetailVO newCartDetaiilByUsername(Integer id, String username) {
//                 CartDetail cartDetail = cartDao.getInfoProductByID(id,username).orElseThrow(()
//                         ->
//                         );

        return  null;
    }


}
