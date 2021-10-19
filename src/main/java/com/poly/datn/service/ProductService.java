package com.poly.datn.service;

import com.poly.datn.vo.ProductVO;
import com.poly.datn.entity.Product;


import java.util.List;
import java.util.Optional;

public interface ProductService {

   List<ProductVO> getList(Optional<Integer> cate, Optional<String> find);
   List<Product> getListByCate(Integer cateId);

    ProductVO getById(Integer id) throws NullPointerException;
}
