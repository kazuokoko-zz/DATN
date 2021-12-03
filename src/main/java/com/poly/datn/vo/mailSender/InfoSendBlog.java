package com.poly.datn.vo.mailSender;

import com.poly.datn.vo.AccountVO;
import com.poly.datn.vo.BlogDetailsVO;
import com.poly.datn.vo.CommentVO;
import com.poly.datn.vo.ProductVO;
import lombok.Data;

import java.util.List;

@Data
public class InfoSendBlog {
    public List<String> name;
    public List<String> email;

    private String title;
    private String shortText;
    private String photo;
    private String timeCreated;
    private Integer createdBy;
    List<BlogDetailsVO> blogDetails;
}
