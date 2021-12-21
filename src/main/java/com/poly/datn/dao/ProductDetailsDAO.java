package com.poly.datn.dao;

import com.poly.datn.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailsDAO extends JpaRepository<ProductDetails, Long> {

    List<ProductDetails> findAllByProductIdEquals(Integer id);

    void deleteAllByProductIdEquals(Integer productId);
}
