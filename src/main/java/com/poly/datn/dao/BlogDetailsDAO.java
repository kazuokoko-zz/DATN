package com.poly.datn.dao;

import com.poly.datn.entity.BlogDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogDetailsDAO extends JpaRepository<BlogDetails, Long> {

    @Query("select d from BlogDetails d where d.blogId=:blogId")
    List<BlogDetails> findByBlogId(@Param("blogId") Integer id);
}
