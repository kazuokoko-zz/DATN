package com.poly.datn.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "fullname", nullable = false, length = 30)
    private String fullname;
    @Basic
    @Column(name = "email", length = 50)
    private String email;
    @Basic
    @Column(name = "phone", nullable = false, length = 16)
    private String phone;
    @Basic
    @Column(name = "address", nullable = false, length = 100)
    private String address;
    @Basic
    @Column(name = "note")
    private String note;

//    @OneToOne(mappedBy = "customer",fetch = FetchType.EAGER)
//    Orders orders;

}
