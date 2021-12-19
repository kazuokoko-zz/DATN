package com.poly.datn.service.impl;

import com.poly.datn.dao.AccountRoleDAO;
import com.poly.datn.entity.AccountRole;
import com.poly.datn.service.AccountRoleService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.VoBoSung.Account.AccountRoleVO;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
public class AccountRoleServiceImpl implements AccountRoleService {
    @Autowired
    CheckRole checkRole;

    @Autowired
    AccountRoleDAO accountRoleDAO;

    @Override
    public Object grantRole(AccountRoleVO accountRoleVO, Principal principal) {
        if (principal == null)
            throw  new NotImplementedException("Chưa đăng nhập");
        else if (checkRole.isHavePermition(principal.getName(), "Director")) {
            AccountRole accountRole = new AccountRole();
            BeanUtils.copyProperties(accountRoleVO, accountRole);
            accountRole = accountRoleDAO.save(accountRole);
            BeanUtils.copyProperties(accountRole, accountRoleVO);
            return accountRoleVO;
        } else {
            return null;
        }
    }

    @Override
    public Object addRole(AccountRoleVO accountRoleVO) {
        try {
            AccountRole accountRole = new AccountRole();
            BeanUtils.copyProperties(accountRoleVO, accountRole);
            accountRole = accountRoleDAO.save(accountRole);
            BeanUtils.copyProperties(accountRole, accountRoleVO);
            return accountRoleVO;
        }catch (Exception e){
            throw  new NotImplementedException("Đã xảy ra lỗi khi thêm quyền cho tài khoản, mã lỗi:" + e);
        }

    }
}
