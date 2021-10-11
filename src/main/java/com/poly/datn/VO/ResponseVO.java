package com.poly.datn.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseVO<T> {
    private String messageCode;
    private String messageName;
    private T data;
}
