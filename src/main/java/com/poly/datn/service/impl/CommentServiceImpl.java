package com.poly.datn.service.impl;

import com.poly.datn.dao.CommentDAO;
import com.poly.datn.entity.Comment;
import com.poly.datn.service.CommentService;
import com.poly.datn.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
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

    @Override
    public CommentVO newComment(CommentVO commentVO) {
        Comment comment = new Comment();
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        comment.setTimeCreated(timestamp);
        BeanUtils.copyProperties(commentVO, comment);
        comment = commentDAO.save(comment);
        commentVO.setId(comment.getId());
        commentVO.setTimeCreated(sdf.format(comment.getTimeCreated()));
        return commentVO;
    }

    @Override
    public CommentVO deleteComment(Integer id) {
        return null;
    }

    @Override
    public CommentVO updateComment(CommentVO commentVO) {
        return null;
    }


}
