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

    @Query(value = "select  c from CartDetail  c where c.productId=:productId and c.userId=:userId")
    CartDetail findOneByProductIdAndUserId(@Param("productId") Integer productId, @Param("userId") Integer userId);

    @Query(nativeQuery = true,value ="delete from  cart_detail c where  c.user_id=:uid")
    void removeFromCartById(@Param("uid") Integer uid);
}
