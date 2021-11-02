package com.poly.datn.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVO {

    private Long id;

    private Integer blogId;

    private Byte rate;

    private String timeCreated;

    private Long repliedTo;

    private String name;

    private String email;

    private String detail;


    BlogVO blog;


    List<CommentVO> comments;


    CommentVO reComment;


}
