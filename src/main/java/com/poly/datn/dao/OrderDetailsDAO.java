package com.poly.datn.dao;

import com.poly.datn.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailsDAO extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findAllByOrderIdEquals(Integer orderId);

    @Query("select sum(d.quantity) from OrderDetails d where  d.orderId = :id")
    Integer getCountProductOf(@Param("id") Integer id);

}
