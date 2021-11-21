package com.poly.datn.dao;

import com.poly.datn.entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface ProductColorDAO extends JpaRepository<ProductColor, Integer> {

    List<ProductColor> findAllByProductIdEquals(Integer id);

   ProductColor findByColorId(Integer id);


    void deleteAllByProductIdEquals(Integer productId);
}
