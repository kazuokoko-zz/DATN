package com.poly.datn.service.impl;

import com.poly.datn.common.Constant;
import com.poly.datn.jwt.AuthTokenFilter;
import com.poly.datn.vo.CartDetailVO;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.CartDetailDAO;
import com.poly.datn.dao.ProductDAO;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.service.CartDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    AccountDAO accountDAO;

    @Override
    public List<CartDetailVO> findCartByUsername(Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        }
        List<CartDetailVO> cartDetailVOS = new ArrayList<>();
        cartDao.getCartDetailsByUsername(principal.getName()).forEach(orders -> {
            CartDetailVO vo = new CartDetailVO();
            BeanUtils.copyProperties(orders, vo);
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
        CartDetailVO cartDetailVO = new CartDetailVO();
        Optional<CartDetail> optionalCartDetail = cartDao.findById(id);
        if (optionalCartDetail.isPresent()) {
            CartDetail entity = optionalCartDetail.get();
            BeanUtils.copyProperties(entity, cartDetailVO);
            cartDao.delete(entity);
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public CartDetailVO save(CartDetailVO cartDetailVO, Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        }
        CartDetail cartDetail = new CartDetail();
        if (cartDetailVO.getId() == null) {
            BeanUtils.copyProperties(cartDetailVO, cartDetail);
            cartDetail.setUserId(accountDAO.findAccountByUsername(principal.getName()).getId());
            cartDetail = cartDetailDAO.save(cartDetail);

        } else if (cartDetailVO.getQuantity() <= 0) {
            cartDetailDAO.deleteById(cartDetailVO.getId());
            return null;
        } else {
            cartDetail = cartDetailDAO.getById(cartDetailVO.getId());
            cartDetail.setQuantity(cartDetailVO.getQuantity());
            cartDetailDAO.save(cartDetail);
        }
        BeanUtils.copyProperties(cartDetail, cartDetailVO);
        return cartDetailVO;

    }


}
