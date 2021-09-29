package com.poly.datn.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryTypeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;
}
