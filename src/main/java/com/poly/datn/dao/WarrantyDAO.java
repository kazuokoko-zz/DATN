package com.poly.datn.dao;

import com.poly.datn.entity.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarrantyDAO extends JpaRepository<Warranty, Integer> {
    List<Warranty> findByOrderId(Integer orderId);
}
