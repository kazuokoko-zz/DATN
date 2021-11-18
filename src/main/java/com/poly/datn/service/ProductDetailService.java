package com.poly.datn.service;

import com.poly.datn.vo.ProductDetailsVO;

import java.security.Principal;
import java.util.List;

public interface ProductDetailService {
    List<ProductDetailsVO> newProductDetail(List<ProductDetailsVO> productDetailsVO, Principal principal);
}
