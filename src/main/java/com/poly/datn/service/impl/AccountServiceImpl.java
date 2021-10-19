package com.poly.datn.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.datn.vo.AccountVO;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDAO accountDAO;

    @Override
    public AccountVO updateAccount(JsonNode jsonNode, Principal principal) {
        if (principal == null) return null;
        ObjectMapper mapper = new ObjectMapper();

        AccountVO accountVO = mapper.convertValue(jsonNode, AccountVO.class);
        Account account = accountDAO.findAccountByUsername(principal.getName());
        account.setFullname(accountVO.getFullname());
        account.setAddress(accountVO.getAddress());
        account.setPhone(accountVO.getPhone());
        account = accountDAO.save(account);
        BeanUtils.copyProperties(account, accountVO);
        return accountVO;
    }

    @Override
    public AccountVO findByUsername(Principal principal) {
        if (principal == null) return null;
        AccountVO accountVO = new AccountVO();
        Account account = accountDAO.findAccountByUsername(principal.getName());
        BeanUtils.copyProperties(account, accountVO);
        return accountVO;
    }

}
