package com.poly.datn.dao;

import com.poly.datn.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDetailsDAO extends JpaRepository<ProductDetails, Long> {

    List<ProductDetails> findAllByProductIdEquals(Integer id);

    @Query(nativeQuery = true,value = "delete from product_details as p where p.product_id = :id")
    void deleteAllByProductIdEquals(@Param("id") Integer productId);
}
