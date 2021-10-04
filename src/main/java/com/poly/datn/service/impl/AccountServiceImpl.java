package com.poly.datn.service.impl;

import com.poly.datn.VO.AccountVO;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDAO accountDAO;

    @Override
    public AccountVO findByUsername(String username) {
        AccountVO accountVO = new AccountVO();
        Account account = accountDAO.findAccountByUsername(username);
        BeanUtils.copyProperties(account, accountVO);
        return  accountVO;
    }

}
