package com.poly.datn.service;


import com.poly.datn.VO.AccountVO;

public interface AccountService {

    AccountVO findByUsername(String username);
}
