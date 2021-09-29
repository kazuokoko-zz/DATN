package com.poly.datn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Account_role", schema = "bhoddvjk1na7d8a0xtlr", catalog = "")
public class AccountRole {
    private int id;
    private int accountId;
    private int roleId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "account_id", nullable = false)
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "role_id", nullable = false)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountRole that = (AccountRole) o;
        return id == that.id && accountId == that.accountId && roleId == that.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, roleId);
    }
}
