package com.poly.datn.dao;

import com.poly.datn.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDetailsDAO extends JpaRepository<ProductDetails, Long> {

    @Query("select  p from ProductDetails  p where  p.productId=:id")
    List<ProductDetails> getByProductId(int id);
}
