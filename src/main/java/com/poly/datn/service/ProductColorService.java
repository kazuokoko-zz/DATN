package com.poly.datn.service;

import com.poly.datn.dao.ProductColorDAO;
import com.poly.datn.entity.ProductColor;
import com.poly.datn.vo.ProductColorVO;

import java.security.Principal;
import java.util.List;

public interface ProductColorService {
    List<ProductColorVO> newProductColor(List<ProductColorVO> productColorVOS, Principal principal);
}
