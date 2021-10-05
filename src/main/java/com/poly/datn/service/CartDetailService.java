package com.poly.datn.service;

import com.poly.datn.VO.CartDetailVO;
import com.poly.datn.entity.CartDetail;

import java.util.List;

public interface CartDetailService {
    List<CartDetailVO>  findCartByUsername(String username);
    CartDetailVO getCartDetailById(CartDetailVO cartDetail);
    CartDetailVO deleteCartDetaiilById(Integer id);
    CartDetailVO newCartDetaiilByUsername(Integer id, String username);
}
