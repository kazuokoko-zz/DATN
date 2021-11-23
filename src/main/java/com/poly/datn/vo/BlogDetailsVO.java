package com.poly.datn.vo;

import lombok.Data;


@Data
public class BlogDetailsVO {

    private Long id;
    private Integer blogId;
    private String type;
    private Short ordinal;
    private String content;


    BlogVO blog;

}
