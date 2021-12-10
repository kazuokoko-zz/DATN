package com.poly.datn.service;

import com.poly.datn.vo.ProductCategoryVO;
import com.poly.datn.vo.ProductVO;
import com.poly.datn.entity.Product;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Object delete(Integer id, Principal principal);

    Object dontSell(Integer id, Principal principal);

    List<ProductVO> getList(Optional<Integer> cate, Optional<String> find);
    List<ProductVO> getListAdmin(Optional<Integer> cate, Optional<String> find);

    List<ProductVO> getListDeleteAdmin(Optional<Integer> cate, Optional<String> find);

    List<ProductVO> getMostNew();

    List<Product> getListByCate(Integer cateId);

    ProductVO getById(Integer id) throws NullPointerException;

    List<ProductVO> getTrending();

    ProductVO newProduct(ProductVO productVO, Principal principal);

    ProductVO update(ProductVO productVO, Principal principal);

    List<ProductVO> getByPrice(Optional<Long> start, Optional<Long> end);

    ProductCategoryVO selectCate(Integer pid, Integer cid, Principal principal);

    Boolean unSelectCate(Integer pid, Integer cid, Principal principal);
}
