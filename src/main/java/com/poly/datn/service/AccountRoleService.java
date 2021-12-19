package com.poly.datn.service;

import com.poly.datn.vo.VoBoSung.Account.AccountRoleVO;

import java.security.Principal;

public interface AccountRoleService {
    Object grantRole(AccountRoleVO accountRoleVO, Principal principal);
}
