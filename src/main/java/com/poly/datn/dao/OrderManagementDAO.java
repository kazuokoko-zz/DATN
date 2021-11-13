package com.poly.datn.dao;

import com.poly.datn.entity.OrderManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderManagementDAO extends JpaRepository<OrderManagement, Long> {
}
