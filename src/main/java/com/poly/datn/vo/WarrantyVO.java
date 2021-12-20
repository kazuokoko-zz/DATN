package com.poly.datn.vo;


import com.poly.datn.common.RegexEmail;
import com.poly.datn.vo.VoBoSung.OrderDTO.OrdersVO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class WarrantyVO extends RegexEmail {

    private Integer id;

    @Size(min = 1, message = "Mã đơn hàng không được để trống")
    private Integer orderId;

    @Size(min = 1, message = "Số seri sản phẩm không được để trống")
    private String productSeri;

    @NotNull
    private Integer productId;
    @NotNull
    private Integer colorId;

    @NotNull
    private Date expiredDate;

    private String name;

    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH)
    private String phone;

    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH)
    private String address;

    private Integer countWarranty;

    private Integer status;

    List<WarrantyInvoiceVO> warrantyInvoiceVOS;

    OrdersVO orders;

}
