package com.poly.datn.VO;

import com.poly.datn.dao.CartDetailDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.entity.CartDetail;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.Optional;


@Data
public class CartDetailVO {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Double price;

    private Integer saleId;

    AccountVO account;

    ProductVO product;


}
