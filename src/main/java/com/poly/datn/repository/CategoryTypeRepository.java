package com.poly.datn.repository;

import com.poly.datn.entities.Category_type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryTypeRepository extends JpaRepository<Category_type, Integer> {

}
