package com.poly.datn.dao;

import com.poly.datn.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findAllByCategoryIdEquals(Integer integer);

    void deleteAllByProductIdEquals(Integer productId);

    List<ProductCategory> findAllByProductIdEquals(Integer productId);

    ProductCategory findOneById(Long productId);

    @Query("delete from ProductCategory c where c.productId=:pid and c.categoryId=:cid")
    void unSelect(@Param("pid") Integer productId, @Param("cid") Integer categoryId);
}
