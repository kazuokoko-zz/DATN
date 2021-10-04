package com.poly.datn.VO;

import lombok.Data;


import java.util.List;

@Data
public class RoleVO {

    private Integer id;

    private String roleName;




    List<AccountRoleVO> accountRoles;


}
