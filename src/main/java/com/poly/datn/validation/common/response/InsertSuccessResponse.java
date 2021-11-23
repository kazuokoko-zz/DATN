package com.poly.datn.validation.common.response;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertSuccessResponse extends SuccessResponse {
  @NotNull
  private String id;
}
