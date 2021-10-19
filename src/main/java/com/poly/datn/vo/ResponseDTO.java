package com.poly.datn.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDTO<T>  {
    private String code;
    private String message;
    private T object;
}
