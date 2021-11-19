package com.poly.datn.service;

import com.poly.datn.vo.FavoriteVO;
import com.poly.datn.vo.ProductVO;

import java.security.Principal;
import java.util.List;

public interface FavoriteService {
    List<ProductVO> getFavorites(Principal principal);
    Object addToFavorite(FavoriteVO favoriteVO, Principal principal);
}
