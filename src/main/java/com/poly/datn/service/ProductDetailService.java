package com.poly.datn.service;

import com.poly.datn.vo.ProductDetailsVO;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductDetailService {
    List<ProductDetailsVO> newProductDetail(Optional<Integer> id, List<ProductDetailsVO> productDetailsVOS, Principal principal);
}
