package com.poly.datn.service.impl;

import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.FavoriteDAO;
import com.poly.datn.dao.ProductDAO;
import com.poly.datn.dao.ProductDetailsDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.entity.Favorite;
import com.poly.datn.entity.ProductDetails;
import com.poly.datn.service.FavoriteService;
import com.poly.datn.vo.FavoriteVO;
import com.poly.datn.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteDAO favoriteDAO;

    @Autowired
    ProductDetailsDAO productDetailsDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    AccountDAO accountDAO;


    @Override
    public List<ProductVO> getFavorites(Principal principal) {
        if (principal == null)
            return null;
        List<ProductVO> productVO2 = new ArrayList<>();
        for (Favorite favorite : favoriteDAO.findByAccountUsername(principal.getName())) {
            List<String> photos = new ArrayList<>();
            productDAO.getProductById(favorite.getProductId()).forEach(productVO ->
            {
                ProductVO productVOS = new ProductVO();
//               productVO = productVO.add(product1);
                BeanUtils.copyProperties(productVO, productVOS);
                for (ProductDetails productDetails : productDetailsDAO.findAllByProductId(productVO.getId())) {
                    if (productDetails.getPropertyName().equalsIgnoreCase("photo")) {
                        for (String photo : productDetails.getPropertyValue().split(",")) {
                            photos.add(photo.trim());
                        }
                    }
                }
                productVOS.setPhotos(photos);
                productVO2.add(productVOS);
            });
        }
        return productVO2;
    }

    @Override
    public Object addToFavorite(FavoriteVO favoriteVO, Principal principal) {
        if (principal == null)
            return null;
        Account account = accountDAO.findAccountByUsername(principal.getName());
        if (account == null)
            return null;

        Favorite favorite = new Favorite();
        favorite.setAccountId(account.getId());
        favorite.setProductId(favoriteVO.getProductId());
        Optional<Favorite> opt = favoriteDAO.findByProductIdEqualsAndAccountIdEquals(favoriteVO.getProductId(), account.getId());
        if (opt.isPresent()) {
            favoriteDAO.deleteById(opt.get().getId());
            return false;
        } else {
            favoriteDAO.save(favorite);
            return true;
        }

    }
}
