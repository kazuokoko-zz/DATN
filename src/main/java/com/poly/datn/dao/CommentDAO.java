package com.poly.datn.dao;

import com.poly.datn.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDAO extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBlogIdEquals(Integer blogId);

    List<Comment> findAllByRepliedToEquals(Long repliedTo);
}
