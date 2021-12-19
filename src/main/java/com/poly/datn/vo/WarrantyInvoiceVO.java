package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;


@Data
public class WarrantyInvoiceVO extends RegexEmail {

    private Integer id;

    @NotNull
    private Long price;

    @Pattern(regexp = ValidName, message = "tên sai định dạng")
    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH, message = "tên tối thiểu 5 ký tự và tối đa 100 ký tự")
    private String name;

    @Pattern(regexp = ValidPhone,message = "Số điện thoại sai định dạng")
    private String phone;

    @Size(min = 10, message = "địa chỉ tối thiểu 10 ký tự")
    private String address;

    @Size(min = 1, message = "Mã hóa đơn bảo hành không được để trống")
    private Integer warrantyId;

    @Size(min = 5, message = "địa chỉ tối thiểu 5 ký tự")
    private String productSeri;

    private Integer productId;

    private Integer colorId;

    private Date expiredDate;

    @Size(min = 5, message = "Đơn vị bảo hàng nhập tối thiểu 5 ký tự")
    private String warrantyUnit;

    private String createBy;

    @NotNull
    private String productState;

    @NotNull
    private String type;
}
