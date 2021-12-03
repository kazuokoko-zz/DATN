package com.poly.datn.dao;

import com.poly.datn.entity.ProductSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSaleDAO extends JpaRepository<ProductSale, Integer> {

    Optional<ProductSale> findByProductIdEqualsAndSaleIdEquals(Integer productId, Integer saleId);


    ProductSale findByProductIdAndSaleId(Integer productId, Integer saleId);
    List<ProductSale> findByProductId(Integer productId);
}
