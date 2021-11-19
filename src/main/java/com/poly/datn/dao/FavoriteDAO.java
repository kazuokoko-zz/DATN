package com.poly.datn.dao;

import com.poly.datn.entity.Favorite;
import com.poly.datn.vo.FavoriteVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteDAO extends JpaRepository<Favorite, Long> {
  @Query("select f from Favorite f where f.account.username=?1")
  List<Favorite> findByAccountUsername(String username);

  @Query("select f from Favorite f where f.account.id=:accountId and f.product.id=:productId")
  Favorite findFavoriteByAccountIdAndProductId(Integer accountId, Integer productId);

  Optional<Favorite>  findByProductIdEqualsAndAccountIdEquals(Integer productId, Integer accountId);

  Favorite findByProductId(Integer productId);

}
