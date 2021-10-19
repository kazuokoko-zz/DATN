package com.poly.datn.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "username", nullable = false, length = 30)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 30)
    private String password;
    @Basic
    @Column(name = "fullname", nullable = false, length = 30)
    private String fullname;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "phone", nullable = false, length = 16)
    private String phone;
    @Basic
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Basic
    @Column(name = "user_status", nullable = false)
    private Boolean userStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

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



    public List<Blog> getBlogs() {
        return blogs;
    }

    public List<AccountRole> getAccountRoles() {
        return accountRoles;
    }

    public List<OrderManagement> getOrderManagements() {
        return orderManagements;
    }

    public List<QuantityManagerment> getQuantityManagerments() {
        return quantityManagerments;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }
}
