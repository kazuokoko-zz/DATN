package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class CustomerVO extends RegexEmail {

    private Long id;
    @Size(min = 3)
    private String fullname;

    @Pattern(regexp = RegexEmail.regexE, message = "Email sai")
    private String email;

    @Pattern(regexp = ValidNum,message = "Số điện thoại sai")
    private String phone;

    @NotNull
    @NotBlank
    private String address;
    @NotNull
    @NotBlank
    private String note;

    OrdersVO orders;


}
