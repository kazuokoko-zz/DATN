package com.poly.datn.service;

import com.poly.datn.entity.Customer;
import com.poly.datn.vo.CustomerVO;

import java.security.Principal;
import java.util.List;

public interface CustomerService {
    List<CustomerVO> getAllCustomer(Principal principal);
    CustomerVO updateCustomer(CustomerVO customerVO, Principal principal);
    CustomerVO getCustomerById(Long id, Principal principal) throws NullPointerException;
}
