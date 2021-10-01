package com.poly.datn.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "role_name", nullable = false, length = 20)
    private String roleName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }



    @OneToMany(mappedBy = "role")
    List<AccountRole> accountRole;

    public List<AccountRole> getAccountRole() {
        return accountRole;
    }
}
