package com.poly.datn.entity;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Account_role")
public class AccountRole {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "account_id", nullable = false)
    private Integer accountId;
    @Basic
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }


    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    public Account getAccount() {
        return account;
    }

    public Role getRole() {
        return role;
    }
}
