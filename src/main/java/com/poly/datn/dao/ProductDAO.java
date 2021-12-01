package com.poly.datn.dao;

import com.poly.datn.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    List<Product> getProductById(Integer id);

    Product getOneProductById(Integer id);

    @Query(value = "select  * from product  order by name limit 8", nativeQuery = true)
    List<Product> findTrend();

    List<Product> findAllByPriceBetween(Long low, Long high);

    List<Product> findAllByPriceLessThanEqual(Long high);

    List<Product> findAllByPriceGreaterThanEqual(Long low);
}
