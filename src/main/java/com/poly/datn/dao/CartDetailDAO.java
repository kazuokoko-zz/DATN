package com.poly.datn.dao;

import com.poly.datn.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
//@Repository
@EnableJpaRepositories
public interface CartDetailDAO extends JpaRepository<CartDetail, Integer> {

    @Query(value = "select  c from CartDetail  c where c.account.username=?1")
    List<CartDetail> getCartDetailsByUsername(String username);
    @Query(value = "select  c from CartDetail  c where c.product.id=?1 and c.account.id=?1")
    Optional<CartDetail> getInfoCartByIDProductAndUsername(Integer id, String username);

//    Optional<CartDetail> getInfoProductByID(int id);
//    Optional<CartDetail> getCartDetailsByUsernames(String username);

//    CartDetail save(CartDetail cartDetail, Principal principal);
//    CartDetail getCartById(Integer id);
}
