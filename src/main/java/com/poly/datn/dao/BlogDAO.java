package com.poly.datn.dao;

import com.poly.datn.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogDAO extends JpaRepository<Blog, Integer> {
    @Query("select  b from Blog b where b.productId =: productId and b.type =:type")
    Blog getByProductIdAndType(@Param("productId") Integer productId, @Param("type") Integer type);
}
