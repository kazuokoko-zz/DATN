package com.poly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "account_role")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false ,updatable  = false)
    @JsonIgnore
    Account account;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false ,updatable  = false)
    @JsonIgnore
    Role role;

    public Account getAccount() {
        return account;
    }

    public Role getRole() {
        return role;
    }
}
