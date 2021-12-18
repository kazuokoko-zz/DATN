package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountRegisterVO extends RegexEmail {
    public static final int NAME_MAX_LENGTH = 50;
    public static final int NAME_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 255;
    public static final int PASSWORD_MIN_LENGTH = 6;

    @Pattern(regexp = RegexU, message = "username không có dấu cách, 5 -20 ký tự, được phép có (.) hoặc (_), không bao gồm ký tự đăục biệt khác")
    @Size(max = NAME_MAX_LENGTH, min = NAME_MIN_LENGTH)
    private String username;

    @Size(max = PASSWORD_MAX_LENGTH, min = PASSWORD_MIN_LENGTH)
    @Pattern(regexp = RegexP, message = "Mật khẩu tối thiểu 6 ký tự, bao gồm chữ hoa, chữ thường, số, ký tự đặc biệt")
    private String password;

    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH, message = "tên tối thiểu 5 ký tự và tối đa 100 ký tự")
    private String fullname;

    @Pattern(regexp = regexE, message = "email phải có định dạng email@email.com hoặc email@email.com.vn")
    private String email;
}
