package com.poly.datn.dao;

import com.poly.datn.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerDAO extends JpaRepository<Customer, Long> {


}
