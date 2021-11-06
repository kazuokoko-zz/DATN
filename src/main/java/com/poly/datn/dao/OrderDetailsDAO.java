package com.poly.datn.dao;

import com.poly.datn.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsDAO extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findAllByOrderIdEquals(Integer orderId);
}
