package com.poly.datn.service;

import com.poly.datn.vo.ProductColorVO;
import com.poly.datn.vo.VoBoSung.ProductDTO.UpdateProductColorDTO;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductColorService {
    void  updateProductColor(Integer id, List<UpdateProductColorDTO> productColorVOS);
    List<ProductColorVO> newProductColor(Optional<Integer> id, List<ProductColorVO> productColorVOS,  Optional<String>  statusProduct,Principal principal);
}
