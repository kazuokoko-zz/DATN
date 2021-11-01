package com.poly.datn.service;

import com.poly.datn.vo.CommentVO;

import java.util.List;

public interface CommentService {
    List<CommentVO> getListByBlogId(Integer bid);
    CommentVO newComment(CommentVO commentVO);
    CommentVO deleteComment(Integer id);
    CommentVO updateComment(CommentVO commentVO);
}
