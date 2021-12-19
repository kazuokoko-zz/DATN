package com.poly.datn.vo.VoBoSung.Account;

import com.poly.datn.common.FieldMatch;
import com.poly.datn.common.RegexEmail;
import lombok.Data;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "newPassWord", second = "reNewPassWord", message = "Nhập lại mật khẩu không chính xác")
public class ModifyPassworDTO extends RegexEmail {
    @Size(min = 1,message = "không được bỏ trống mật khẩu cũ")
    public String oldPassWord;
    @Pattern(regexp = RegexP, message = "Mật khẩu mới phải bao gồm: Chữ hoa, chữ thường, ký tự đặc biệt, tối thiểu 6 ký tự")
    @Size(min = 6, message = "Mật khẩu mới phải bao gôm: Chữ hoa, chữ thường, ký tự đặc biệt, tối thiểu 6 ký tự")
    public String newPassWord;

    @Size(min = 1,message = "nhập lại mật khẩu không chính xác")
    public String reNewPassWord;
}
