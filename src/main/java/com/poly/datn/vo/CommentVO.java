package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CommentVO extends RegexEmail {

    private Long id;

    @NotNull(message = "Id blog không được để trống")
    private Integer blogId;

    private Byte rate;

    private String timeCreated;

    private Long repliedTo;

    @Pattern(regexp = ValidName, message = "tên sai định dạng")
    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH, message = "tên tối thiểu 5 ký tự và tối đa 100 ký tự")
    private String name;
    @Pattern(regexp = regexE, message = "email phải có định dạng email@email.com hoặc email@email.com.vn")
    private String email;
    @NotNull(message = "Nội dung không được để trống")
    private String detail;


    BlogVO blog;


    List<CommentVO> comments;


    CommentVO reComment;


}
