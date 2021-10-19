package com.poly.datn.service;

import com.poly.datn.vo.ProductCategoryVO;

import java.util.List;

public interface ProductCategoryService {

     List<ProductCategoryVO> getByCategoryId(Integer integer) ;
}
