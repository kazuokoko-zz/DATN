package com.poly.datn.dao;

import com.poly.datn.entity.OrderManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderManagementDAO extends JpaRepository<OrderManagement, Long> {
    List<OrderManagement> findByOrderId(Integer orderId);
}
