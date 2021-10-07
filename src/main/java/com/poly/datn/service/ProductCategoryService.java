package com.poly.datn.service;

import com.poly.datn.VO.ProductCategoryVO;
import com.poly.datn.entity.Product;

import java.util.List;

public interface ProductCategoryService {

     List<ProductCategoryVO> getByCategoryId(Integer integer) ;
}
