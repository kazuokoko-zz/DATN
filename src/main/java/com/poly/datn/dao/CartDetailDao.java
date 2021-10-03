package com.poly.datn.dao;

import com.poly.datn.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartDetailDao extends JpaRepository<CartDetail, Integer>{
    @Query("select  c from CartDetail  c where c.id = :id")
    List<CartDetail> findCartDetailsByUsername(@Param("id") Integer id);
}
