package com.poly.datn.service;

import com.poly.datn.VO.CartDetailVO;

import java.util.List;

public interface CartDetailService {
    List<CartDetailVO>  findCartByUsername(String username);
//    CartDetail getById(Integer id);
}
