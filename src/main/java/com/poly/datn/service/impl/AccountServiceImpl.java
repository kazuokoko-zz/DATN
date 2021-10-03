package com.poly.datn.service.impl;

import com.poly.datn.dao.AccountDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDAO accountDAO;

    @Override
    public Account findByUsername(String username) {
        return accountDAO.findAccountByUsername(username);
    }
}
