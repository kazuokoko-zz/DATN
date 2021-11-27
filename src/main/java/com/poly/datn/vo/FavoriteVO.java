package com.poly.datn.vo;

import lombok.Data;

@Data
public class FavoriteVO {
   private Integer id;
   private Integer accountId;
   private Integer productId;
   private String nameProduct;
   AccountVO account;
   ProductVO product;
}
