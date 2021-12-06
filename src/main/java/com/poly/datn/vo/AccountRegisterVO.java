package com.poly.datn.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountRegisterVO {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String fullname;
    @NotNull
    private String email;
}
