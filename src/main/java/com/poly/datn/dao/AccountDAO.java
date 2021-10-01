package com.poly.datn.dao;

import com.poly.datn.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AccountDAO extends JpaRepository<Account, Integer> {

   @Query("select a from Account  a where  a.username =:username")
   Account findAccountByUsername(String username);
}
