package com.poly.datn.dao;

import com.poly.datn.entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface ProductColorDAO extends JpaRepository<ProductColor, Integer> {
    @Query("SELECT  p from ProductColor p where  p.productId=:id")
    List<ProductColor> getByProductId(@Param("id") Integer id);

   ProductColor findByColorId(Integer id);
}
