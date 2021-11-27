package com.poly.datn.vo;

import lombok.Data;

import java.util.List;
@Data
public class AccountVO {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Boolean userStatus;


    List<BlogVO> blogs;
    List<AccountRoleVO> accountRoles;
    List<OrderManagementVO> orderManagements;
    List<QuantityManagermentVO> quantityManagerments;
    List<CartDetailVO> cartDetails;


}
