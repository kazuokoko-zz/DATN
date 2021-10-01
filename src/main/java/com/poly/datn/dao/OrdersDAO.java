package com.poly.datn.dao;

import com.poly.datn.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersDAO extends JpaRepository<Orders, Integer> {

    @Query("select  o from Orders  o where o.username = :username")
    List<Orders> getByUsername(@Param("username") String username);
}
