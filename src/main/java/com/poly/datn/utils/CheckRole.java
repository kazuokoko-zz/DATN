package com.poly.datn.utils;

import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.AccountRoleDAO;
import com.poly.datn.dao.RoleDAO;
import com.poly.datn.entity.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckRole {

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    AccountRoleDAO accountRoleDAO;


    public Boolean isHavePermition(String username, String role) {
        List<AccountRole> accountRoles = accountRoleDAO.findAllByAccountIdEquals(accountDAO.findByUsername(username).get().getId());
        for (AccountRole accountRole : accountRoles) {
            if (roleDAO.getById(accountRole.getRoleId()).getRoleName().equalsIgnoreCase(role))
                return true;
        }
        return false;
    }

}
