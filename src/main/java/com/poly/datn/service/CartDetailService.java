package com.poly.datn.service;

import com.poly.datn.VO.CartDetailVO;
import com.poly.datn.VO.ProductVO;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.entity.Product;

import java.security.Principal;
import java.util.List;

public interface CartDetailService {
    List<CartDetailVO>  findCartByUsername(Principal principal);
    CartDetailVO save(CartDetailVO cartDetail,Principal principal);
    boolean deleteCartDetaiilById(Integer id,Principal principal);
//    CartDetailVO newCartDetaiilByUsername(Integer idProduct, Principal principal);
}
