package com.poly.datn.vo;

import lombok.Data;


import java.util.List;

@Data
public class RoleVO {

    private Integer id;

    private String roleName;

    List<AccountRoleVO> accountRoles;


}
