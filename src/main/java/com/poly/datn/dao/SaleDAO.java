package com.poly.datn.dao;

import com.poly.datn.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDAO extends JpaRepository<Sale, Integer> {
}
