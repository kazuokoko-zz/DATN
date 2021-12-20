package com.poly.datn.service;

import com.poly.datn.vo.ProductCategoryVO;
import com.poly.datn.vo.ProductVO;
import com.poly.datn.entity.Product;
import com.poly.datn.vo.VoBoSung.ProductDTO.UpdateProductDTO;
import org.springframework.transaction.annotation.Transactional;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Object delete(Integer id, Principal principal);

    Object dontSell(Integer id, Principal principal);
//    Object dontSell(Integer id, Principal principal);
//    Object outOfStock(Integer id, Principal principal);

    List<ProductVO> getList(Optional<Integer> cate, Optional<String> find);

    List<ProductVO> getListAdmin(Optional<Integer> cate, Optional<String> find, Principal principal);

    List<ProductVO> getListDeleteAdmin(Optional<Integer> cate, Optional<String> find, Principal principal);

    List<ProductVO> getMostNew();

    List<Product> getListByCate(Integer cateId);

    ProductVO getById(Integer id) throws NullPointerException;

    List<ProductVO> getTrending();

    @Transactional
    Object storageEmpty(Integer id, Principal principal);

    @Transactional
    Object comingsoon(Integer id, Principal principal);

    @Transactional
    Object productReady(Integer id, Principal principal);

    @Transactional
    Object noSell(Integer id, Principal principal);

    ProductVO newProduct(ProductVO productVO, Principal principal);

    ProductVO update(UpdateProductDTO productVO, Principal principal);

    List<ProductVO> getByPrice(Optional<Long> start, Optional<Long> end);

    ProductCategoryVO selectCate(Integer pid, Integer cid, Principal principal);

    Boolean unSelectCate(Integer pid, Integer cid, Principal principal);

    Object getBlogLess(Principal principal);

    List<ProductVO> getDiscount();
}
