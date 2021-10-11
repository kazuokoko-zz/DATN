package com.poly.datn.VO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDTO<T> {
    private T object;
}
