package com.poly.datn.jwt.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Calendar;

@Data
public class ResetPassworDTO {
    private String passwordresetKey;
    private String email;
    private Integer id;
    private String username;
    private String password;
    private Long timecreate;
    private Integer click;
}
