package com.poly.datn.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDTO<T> {
    private T object;
}
