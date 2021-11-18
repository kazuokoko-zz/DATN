package com.poly.datn.service;

import com.poly.datn.vo.ProductVO;
import com.poly.datn.entity.Product;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

     Object delete(Integer id, Principal principal) ;

    List<ProductVO> getList(Optional<Integer> cate, Optional<String> find);

    List<Product> getListByCate(Integer cateId);

    ProductVO getById(Integer id) throws NullPointerException;

    List<ProductVO> getTrending();

    ProductVO newProduct(ProductVO productVO, Principal principal);

}
