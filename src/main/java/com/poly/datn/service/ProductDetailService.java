package com.poly.datn.service;

import com.poly.datn.vo.ProductDetailsVO;
import com.poly.datn.vo.VoBoSung.ProductDTO.UpdateProductDetailDTO;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductDetailService {
    void updateProductDetail(Integer id, List<UpdateProductDetailDTO> productDetailsVOS);
    List<ProductDetailsVO> newProductDetail(Optional<Integer> id, List<ProductDetailsVO> productDetailsVOS, Principal principal);
}
