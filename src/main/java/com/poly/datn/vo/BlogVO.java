package com.poly.datn.vo;

import com.poly.datn.vo.VoBoSung.Account.AccountVO;
import lombok.Data;

import java.util.List;

@Data
public class BlogVO {

    private Integer id;
    private String title;
    private String shortText;
    private String photo;
    private String timeCreated;
    private Integer createdBy;
    private Integer type;
    private Integer productId;
    private Boolean status;



    AccountVO account;
    ProductVO product;
    List<CommentVO> comments;
    List<BlogDetailsVO> blogDetails;


}
