package com.poly.datn.service;

import com.poly.datn.entity.CartDetail;

import java.util.List;

public interface CartService {
    List<CartDetail>  findCartByUsername(Integer id);
//    CartDetail getById(Integer id);
}
