package com.poly.datn.dao;

import com.poly.datn.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ColorDAO extends JpaRepository<Color, Integer> {
    Color findByColorName(String name);
    Color findColorById(Integer id);

}
