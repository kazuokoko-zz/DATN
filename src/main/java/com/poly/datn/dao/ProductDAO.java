package com.poly.datn.dao;

import com.poly.datn.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO  extends JpaRepository<Product, Integer> {
     List<Product> getProductById(Integer id);
     Product getOneProductById(Integer id);
}
