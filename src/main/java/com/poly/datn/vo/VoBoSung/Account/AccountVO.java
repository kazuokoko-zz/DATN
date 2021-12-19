package com.poly.datn.vo.VoBoSung.Account;

import com.poly.datn.vo.BlogVO;
import com.poly.datn.vo.CartDetailVO;
import com.poly.datn.vo.OrderManagementVO;
import com.poly.datn.vo.QuantityManagermentVO;
import lombok.Data;

import java.util.List;

@Data
public class AccountVO {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private Boolean userStatus;
    private List<String> roles;


    List<BlogVO> blogs;
    List<AccountRoleVO> accountRoles;
    List<OrderManagementVO> orderManagements;
    List<QuantityManagermentVO> quantityManagerments;
    List<CartDetailVO> cartDetails;


}
