package com.poly.datn.service.impl;

import com.poly.datn.common.Constant;
import com.poly.datn.dao.CustomerDAO;
import com.poly.datn.entity.Color;
import com.poly.datn.entity.Customer;
import com.poly.datn.entity.Product;
import com.poly.datn.service.CustomerService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ColorVO;
import com.poly.datn.vo.CustomerVO;
import com.poly.datn.vo.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CheckRole checkRole;

    @Autowired
    CustomerDAO customerDAO;

    @Override
    public List<CustomerVO> getAllCustomer(Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        } else if (checkRole.isHavePermition(principal.getName(), "Director")) {
            List<CustomerVO> customerVOList = new ArrayList<>();
            customerDAO.findAll().forEach(customer -> {
               CustomerVO customerVOS = new CustomerVO();
                BeanUtils.copyProperties(customer, customerVOS);
                customerVOList.add(customerVOS);
            });
            return customerVOList;
        } else {
            return null;
        }
    }

    @Override
    public CustomerVO updateCustomer(CustomerVO customerVO, Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        } else if (checkRole.isHavePermition(principal.getName(), "Director")) {
            Optional<Customer> optionalCustomer = customerDAO.findById(customerVO.getId());
            if (optionalCustomer.isPresent()) {
                Customer entityCusomer = new Customer();
                BeanUtils.copyProperties(customerVO, entityCusomer);
                customerDAO.save(entityCusomer);
            }
            return customerVO;
        } else {
            return null;
        }
    }

    @Override
    public CustomerVO getCustomerById(Long id, Principal principal) throws NullPointerException {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        } else if (checkRole.isHavePermition(principal.getName(), "Director")) {

            Customer customer = customerDAO.findById(id).orElseThrow(() -> new NullPointerException("Customer not found with id: " + id));
            CustomerVO customerVO = new CustomerVO();
            BeanUtils.copyProperties(customer, customerVO);
            return customerVO;
        } else {
            return null;
        }
    }
}
