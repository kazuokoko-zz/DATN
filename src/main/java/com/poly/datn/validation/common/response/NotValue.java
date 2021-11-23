package com.poly.datn.validation.common.response;

import javax.validation.constraints.NotNull;

public class NotValue {
    @NotNull
    private Integer status = 0;
    @NotNull
    private String errorCode;
    @NotNull
    private String errorMsg;
}