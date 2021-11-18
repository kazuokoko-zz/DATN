package com.poly.datn.dao;

import com.poly.datn.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentDAO extends JpaRepository<Payment, Integer> {

    @Query(nativeQuery = true, value = "select * from payment p where p.txnref=:txnRef and SUBSTRING(p.createDate,1,8) =:createDate")
    Payment getByTxnRefToday(@Param("txnRef") String txnRef, @Param("createDate") String createDate);

    @Query(nativeQuery = true, value = "select * from payment p where p.orders_id=:ordersId and SUBSTRING(p.createDate,1,8) =:createDate")
    Payment getByOrdersIdToday(@Param("ordersId") Integer ordersId, @Param("createDate") String createDate);

    Payment getByOrdersIdEquals(Integer ordersId);

    List<Payment> findAllByTxnRef(String txnRef);

    @Query(nativeQuery = true, value = "select * from payment p where p.transactionno=:tranno and SUBSTRING(p.createDate,1,8) =:trandate")
    Payment getByTranNoAndTranDate(@Param("tranno") String tranno, @Param("trandate") String trandate);
}
