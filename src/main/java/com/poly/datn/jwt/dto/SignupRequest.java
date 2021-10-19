package com.poly.datn.jwt.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Set<String> role;
}
