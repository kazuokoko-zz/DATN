package com.poly.datn.service;

import com.poly.datn.vo.CartDetailVO;
import com.poly.datn.vo.VoBoSung.ProductDTO.CheckProductColorDTO;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

public interface CartDetailService {
    List<CartDetailVO>  findCartByUsername(Principal principal);
    boolean checkProductColor(CheckProductColorDTO checkProductColorDTO);
    CartDetailVO save( CartDetailVO cartDetail, Principal principal);
    boolean deleteCartDetaiilById(Integer id,Principal principal);
}
