package com.poly.datn.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
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
