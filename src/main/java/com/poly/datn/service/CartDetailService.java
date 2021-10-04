package com.poly.datn.service;

import com.poly.datn.entity.CartDetail;

import java.util.List;

public interface CartDetailService {
    List<CartDetail>  findCartByUsername(String username);
    CartDetail getCartDetailById(CartDetail cartDetail);
}
