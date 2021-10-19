package com.poly.datn.vo;

import lombok.Data;

@Data
public class AccountRoleVO {

    private Integer id;
    private Integer accountId;
    private Integer roleId;



    AccountVO account;

    RoleVO role;


}
