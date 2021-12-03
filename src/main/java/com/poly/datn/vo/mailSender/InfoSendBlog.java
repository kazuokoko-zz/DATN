package com.poly.datn.vo.mailSender;

import com.poly.datn.vo.BlogDetailsVO;
import lombok.Data;

import java.util.List;

@Data
public class InfoSendBlog {
    public String name;
    public String email;

    private String title;
    private String shortText;
    private String photo;
    private String timeCreated;
    private Integer createdBy;
    List<BlogDetailsVO> blogDetails;
}
