package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import lombok.Data;

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

    private String address;

    private String note;

    OrdersVO orders;


}
