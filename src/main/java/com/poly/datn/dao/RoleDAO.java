package com.poly.datn.dao;

import com.poly.datn.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {

    Optional<Role> findRoleByRoleName(String name);
}
