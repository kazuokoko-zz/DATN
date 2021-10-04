package com.poly.datn.dao;

import com.poly.datn.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface AccountDAO extends JpaRepository<Account, Integer> {
//START OF MA CODE

    @Query("select a from Account   a where  a.username =:username")
    Account findAccountByUsername(String username);

    Optional<Account> findByUsername(String username);

//END OF MA CODE
}
