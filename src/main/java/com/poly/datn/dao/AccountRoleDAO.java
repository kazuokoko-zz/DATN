package com.poly.datn.dao;

import com.poly.datn.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRoleDAO extends JpaRepository<AccountRole, Integer> {

    List<AccountRole> findAllByAccountIdEquals(Integer accountId);
}
