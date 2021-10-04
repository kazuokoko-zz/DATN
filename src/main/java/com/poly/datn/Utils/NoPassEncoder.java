package com.poly.datn.Utils;

import org.springframework.security.crypto.password.PasswordEncoder;

public class NoPassEncoder implements PasswordEncoder {
    private static final PasswordEncoder INSTANCE = new NoPassEncoder();

    public NoPassEncoder() {
    }

    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }

    public static PasswordEncoder getInstance() {
        return INSTANCE;
    }
}
