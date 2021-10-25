package com.poly.datn.service;

import com.poly.datn.entity.Favorite;
import com.poly.datn.vo.FavoriteVO;
import javassist.NotFoundException;

import java.security.Principal;
import java.util.List;

public interface FavoriteService {
    List<FavoriteVO> getFavorites(Principal principal);
    FavoriteVO addToFavorite(FavoriteVO favoriteVO,Principal principal);
}
