package com.poly.datn.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "username", nullable = false, length = 30)
    private String username;
    
    @Column(name = "password", nullable = false, length = 30)
    private String password;
    
    @Column(name = "fullname", nullable = false, length = 30)
    private String fullname;
    
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    
    @Column(name = "phone", length = 16)
    private String phone;
    
    @Column(name = "address",  length = 255)
    private String address;
    
    @Column(name = "user_status", nullable = false)
    private Boolean userStatus;


    @OneToMany(mappedBy = "account")
    List<Favorite> favorites;
    @OneToMany(mappedBy = "account")
    List<Blog> blogs;
    @OneToMany(mappedBy = "account")
    List<AccountRole> accountRoles;
    @OneToMany(mappedBy = "account")
    List<QuantityManagerment> quantityManagerments;
    @OneToMany(mappedBy = "account")
    List<CartDetail> cartDetails;


}
