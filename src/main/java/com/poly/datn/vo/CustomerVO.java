package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import com.poly.datn.vo.VoBoSung.OrderDTO.OrdersVO;
import lombok.Data;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class CustomerVO extends RegexEmail {

    private Long id;
    @Pattern(regexp = ValidName, message = "tên sai định dạng")
    @Size(min = 3, message = "tên tối thiểu 3 ký tự")
    private String fullname;

    @Pattern(regexp = RegexEmail.regexE, message = "Email sai định dạng email@email.email")
    private String email;

    @Pattern(regexp = ValidPhone,message = "Số điện thoại sai định dạng")
    private String phone;

    @Size(min = 10, message = "địa chỉ tối thiểu 10 ký tự")
    private String address;
    private String note;

    OrdersVO orders;


}
