package com.poly.datn.service;

import com.poly.datn.vo.CartDetailVO;

import java.security.Principal;
import java.util.List;

public interface CartDetailService {
    List<CartDetailVO>  findCartByUsername(Principal principal);
    CartDetailVO save(CartDetailVO cartDetail,Principal principal);
    boolean deleteCartDetaiilById(Integer id,Principal principal);
}
