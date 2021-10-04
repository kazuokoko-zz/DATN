package com.poly.datn.VO;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class BlogVO {

    private Integer id;
    private String title;
    private Timestamp timeCreated;
    private String createdBy;
    private Integer type;
    private Integer productId;
    private Boolean status;



    AccountVO account;
    ProductVO product;
    List<CommentVO> comments;
    List<BlogDetailsVO> blogDetails;


}
