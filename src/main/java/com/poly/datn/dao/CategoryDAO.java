package com.poly.datn.dao;

import com.poly.datn.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
    @Query("select  c from Category  c where  c.type = 23 and c.id <> 23")
    List<Category>  findCategories();
}
