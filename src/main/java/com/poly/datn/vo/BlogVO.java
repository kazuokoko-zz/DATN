package com.poly.datn.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class BlogVO {

    private Integer id;
    private String title;
    private String timeCreated;
    private String createdBy;
    private Integer type;
    private Integer productId;
    private Boolean status;



    AccountVO account;
    ProductVO product;
    List<CommentVO> comments;
    List<BlogDetailsVO> blogDetails;


}
