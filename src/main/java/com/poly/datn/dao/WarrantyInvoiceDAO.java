package com.poly.datn.dao;


import com.poly.datn.entity.WarrantyInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarrantyInvoiceDAO extends JpaRepository<WarrantyInvoice, Integer> {
    List<WarrantyInvoice> findByWarrantyId(Integer warrantyId);
}
