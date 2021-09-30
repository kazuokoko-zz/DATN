package com.poly.datn.entity;

import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Account {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Boolean userStatus;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 30)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "fullname", nullable = false, length = 30)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 16)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "user_status", nullable = false)
    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    @OneToMany(mappedBy = "account")
    List<Orders> orders;
    @OneToMany(mappedBy = "account")
    List<Blog> blogs;
    @OneToMany(mappedBy = "account")
    List<AccountRole> accountRoles;
    @OneToMany(mappedBy = "account")
    List<OrderManagement> orderManagements;
    @OneToMany(mappedBy = "account")
    List<QuantityManagerment> quantityManagerments;
    @OneToMany(mappedBy = "account")
    List<CartDetail> cartDetails;
}
