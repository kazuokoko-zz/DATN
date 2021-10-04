package com.poly.datn.VO;

import lombok.Data;


@Data
public class BlogDetailsVO {

    private Long id;
    private Integer blogId;
    private Short type;
    private Short ordinal;
    private String content;


    BlogVO blog;

}
