package com.poly.datn.dao;

import com.poly.datn.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentDAO extends JpaRepository<Payment, Integer> {

    @Query(nativeQuery = true, value = "select p from Payment p where p.txnRef=:txnRef and SUBSTRING(p.createDate,1,8) =:createDate")
    Payment  getByTxnRefToday(@Param("txnRef") String txnRef, @Param("createDate") String createDate);


    List<Payment> findAllByTxnRef(String txnRef);
}
