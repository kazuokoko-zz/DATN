package com.poly.datn.jwt.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private  String username;
    private String password;
    private boolean rememberMe;
}