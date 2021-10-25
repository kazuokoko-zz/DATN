package com.poly.datn.dao;

import com.poly.datn.entity.Color;
import com.poly.datn.entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ColorDAO extends JpaRepository<Color, Integer> {
    Color findByColorName(String name);
    Color findColorById(Integer id);
}
