package com.poly.datn.service;

import com.poly.datn.entity.Account;
import org.springframework.stereotype.Service;


public interface AccountService {

    Account findByUsername(String username);
}
