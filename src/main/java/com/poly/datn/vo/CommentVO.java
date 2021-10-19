package com.poly.datn.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class CommentVO {

    private Long id;

    private Integer blogId;

    private Byte rate;

    private Timestamp timeCreated ;

    private Long repliedTo;

    private String name;

    private String email;

    private Boolean status;

    private String comment;




    BlogVO blog;


    List<CommentVO> comments;


    CommentVO reComment;


}
