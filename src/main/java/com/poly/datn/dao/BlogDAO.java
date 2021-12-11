package com.poly.datn.dao;

import com.poly.datn.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface BlogDAO extends JpaRepository<Blog, Integer> {
    @Query("select  b from Blog b where b.productId =:productId and b.type =:type")
    Optional<Blog> getByProductIdAndType(@Param("productId") Integer productId, @Param("type") Integer type);


    List<Blog> findAllByProductIdEquals(Integer integer);

    List<Blog> findAllByTimeCreatedBetween(Timestamp start, Timestamp end);
//    List<Blog> findAllInHour();
}
