package com.poly.datn.validation.common.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidationErrorResponse extends ErrorResponse {
  private Map<String, String> validateDetails = new HashMap<>();
}
