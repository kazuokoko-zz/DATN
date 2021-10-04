package com.poly.datn.jwt.DTO;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Boolean userStatus;
    private String token;
    private String type = "Bearer";
    private List<String> roles;
}
