package com.poly.datn.jwt;

import com.poly.datn.dao.AccountDAO;
import com.poly.datn.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserdetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccountDAO accountDAO;

    //START OF MA CODE

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String uesrname) throws UsernameNotFoundException {
        Account account = accountDAO.findByUsername(uesrname).orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + uesrname));
        return UserDetailsImpl.build(account);
    }


    //END OF MA CODE
}
