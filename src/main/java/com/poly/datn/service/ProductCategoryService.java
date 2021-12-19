package com.poly.datn.service;

import com.poly.datn.vo.ProductCategoryVO;
import com.poly.datn.vo.VoBoSung.ProductDTO.UpdateProductCategoryDTO;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {

     List<ProductCategoryVO> getByCategoryId(Integer integer) ;
     void updateProductCategory(Integer id, List<UpdateProductCategoryDTO> productCategoryVOS);
     List<ProductCategoryVO> newProductCategory(Optional<Integer> id, List<ProductCategoryVO> productCategoryVOS, Principal principal);
}
