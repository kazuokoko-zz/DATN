package com.poly.datn.vo;


import com.poly.datn.common.RegexEmail;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Data
public class SaleVO extends RegexEmail {


    private Integer id;
    @Pattern(regexp = ValidName, message = "tên sai định dạng")
    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH, message = "tên tối thiểu 5 ký tự và tối đa 100 ký tự")
    private String name;
    @NotNull
    private Timestamp startTime;
    @NotNull
    private Timestamp endTime;
    private String status;

    List<ProductSaleVO> productSaleVO;

}
