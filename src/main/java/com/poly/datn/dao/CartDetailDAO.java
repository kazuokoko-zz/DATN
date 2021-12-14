package com.poly.datn.dao;

import com.poly.datn.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface CartDetailDAO extends JpaRepository<CartDetail, Integer> {

    @Query(value = "select  c from CartDetail  c where c.account.username=?1")
    List<CartDetail> getCartDetailsByUsername(String username);

    @Query(value = "select  c from CartDetail  c where c.productId=:productId and c.userId=:userId and c.colorId=:colorId")
    CartDetail findOneByProductIdAndUserId(@Param("productId") Integer productId, @Param("userId") Integer userId, @Param("colorId") Integer colorId);

}
