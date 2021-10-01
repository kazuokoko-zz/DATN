package com.poly.datn.dao;

import com.poly.datn.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findAllByCategoryIdEquals(Integer integer);
}
