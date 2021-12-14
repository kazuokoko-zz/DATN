package com.poly.datn.vo;

import com.poly.datn.common.RegexEmail;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountRegisterVO extends RegexEmail {
    public static final int NAME_MAX_LENGTH = 50;
    public static final int NAME_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 255;
    public static final int PASSWORD_MIN_LENGTH = 6;

    @NotNull
    @Pattern(regexp = RegexU)
    @Size(max = NAME_MAX_LENGTH, min = NAME_MIN_LENGTH)
    private String username;

    @NotNull
    @Size(max = PASSWORD_MAX_LENGTH, min = PASSWORD_MIN_LENGTH)
    @Pattern(regexp = RegexP)
    private String password;

    @NotNull
    @Size(max = DISPLAY_NAME_MAX_LENGTH, min = DISPLAY_NAME_MIN_LENGTH)
    private String fullname;

    @NotNull
    @Pattern(regexp = regexE)
    private String email;
}
