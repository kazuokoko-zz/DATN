package com.poly.datn.dao;

import com.poly.datn.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartDetailDAO extends JpaRepository<CartDetail, Integer> {

    @Query(value = "select  c from CartDetail  c where c.account.username=?1")
    List<CartDetail> getCartDetailsByUsername(String username);
    @Query(value = "select  c from CartDetail  c where c.product.id=?1")
    Optional<CartDetail> getInfoProductByID(Integer id, String username);
//    CartDetail getCartById(Integer id);
}
