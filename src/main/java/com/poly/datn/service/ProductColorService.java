package com.poly.datn.service;

import com.poly.datn.vo.ProductColorVO;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductColorService {
    List<ProductColorVO> newProductColor(Optional<Integer> id, List<ProductColorVO> productColorVOS,  String statusProduct,Principal principal);
}
