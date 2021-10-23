package com.poly.datn.service.impl;

import com.poly.datn.dao.CommentDAO;
import com.poly.datn.entity.Comment;
import com.poly.datn.service.CommentService;
import com.poly.datn.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");

    @Autowired
    CommentDAO commentDAO;

    @Override
    public List<CommentVO> getListByBlogId(Integer bid) {
        List<Comment> comments = commentDAO.findAllByBlogIdEquals(bid);
        List<CommentVO> commentVOS = new ArrayList<>();
        comments.forEach(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVO.setTimeCreated(sdf.format(comment.getTimeCreated()));
            commentVOS.add(commentVO);
        });
        for (CommentVO commentVO : commentVOS) {
            comments = commentDAO.findAllByRepliedToEquals(commentVO.getId());
            commentVO.setComments(new ArrayList<>());
            comments.forEach(comment -> {
                CommentVO commentVO1 = new CommentVO();
                BeanUtils.copyProperties(comment, commentVO1);
                commentVO1.setTimeCreated(sdf.format(comment.getTimeCreated()));
                commentVO.getComments().add(commentVO1);
            });
        }
        return commentVOS;
    }

}
