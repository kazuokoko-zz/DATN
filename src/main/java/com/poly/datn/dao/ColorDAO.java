package com.poly.datn.dao;

import com.poly.datn.entity.Color;
import com.poly.datn.entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

@EnableJpaRepositories
public interface ColorDAO extends JpaRepository<Color, Integer> {
    Color findByColorName(String name);
    Color findColorById(Integer id);
    @Query( value = "select colorName from Color c where c.id =:id")
    Color findNameById(@Param("id") Integer id);
}
