package com.poly.datn.VO;

import lombok.Data;

@Data
public class AccountRoleVO {

    private Integer id;
    private Integer accountId;
    private Integer roleId;



    AccountVO account;

    RoleVO role;


}
