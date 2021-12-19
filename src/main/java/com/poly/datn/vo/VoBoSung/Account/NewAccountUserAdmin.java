package com.poly.datn.vo.VoBoSung.Account;

import com.poly.datn.common.RegexEmail;
import com.poly.datn.vo.RoleVO;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class NewAccountUserAdmin extends RegexEmail {
    public static final int NAME_MAX_LENGTH = 50;
    public static final int NAME_MIN_LENGTH = 5;

    @Pattern(regexp = RegexU, message = "username không có dấu cách, 5 -20 ký tự, được phép có (.) hoặc (_), không bao gồm ký tự đăục biệt khác")
    @Size(max = NAME_MAX_LENGTH, min = NAME_MIN_LENGTH, message = "username không có dấu cách, 5 -20 ký tự, được phép có (.) hoặc (_), không bao gồm ký tự đăục biệt khác")
    private String username;

    @Size(min = 10, message = "Địa chỉ tối thiểu 10 ký tự")
    private String address;

    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH, message = "tên tối thiểu 5 ký tự và tối đa 100 ký tự")
    @Pattern(regexp = ValidName, message = "tên sai định dạng")
    private String fullname;

    @Pattern(regexp = ValidPhone,message = "Số điện thoại sai định dạng 10 chữ số")
    private String phone;

    @Pattern(regexp = regexE, message = "email phải có định dạng email@email.com hoặc email@email.com.vn")
    private String email;
}
