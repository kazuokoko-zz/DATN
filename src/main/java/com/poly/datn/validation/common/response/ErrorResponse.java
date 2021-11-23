package com.poly.datn.validation.common.response;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  @NotNull
  private Integer status = 0;
  @NotNull
  private String errorCode;
  @NotNull
  private String errorMsg;
}
