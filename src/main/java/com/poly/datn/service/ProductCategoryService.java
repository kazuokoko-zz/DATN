package com.poly.datn.service;

import com.poly.datn.vo.ProductCategoryVO;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {

     List<ProductCategoryVO> getByCategoryId(Integer integer) ;

     List<ProductCategoryVO> newProductCategory(Optional<Integer> id, List<ProductCategoryVO> productCategoryVOS, Principal principal);
}
