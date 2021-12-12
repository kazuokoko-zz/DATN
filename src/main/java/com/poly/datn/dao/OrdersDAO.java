package com.poly.datn.dao;

import com.poly.datn.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrdersDAO extends JpaRepository<Orders, Integer> {

    @Query("select  o from Orders  o where o.username = :username")
    List<Orders> getByUsername(@Param("username") String username);

    Optional<Orders> findByIdAndUsername(Integer id, String username);

    @Query(nativeQuery = true, value = "select * from orders c where" +
            " c.id in(select order_id from warranty w where w.order_id =:order_id " +
            "and w.product_id =:product_id)")
    Orders findOneByIdForWarranty(@Param("order_id") Integer order_id, @Param("product_id") Integer product_id);



    @Query(nativeQuery = true, value = "select  * from orders o where o.id in :ids")
    List<Orders> getOrdersOfIds(@Param("ids") List<Integer> ids);

    @Query(nativeQuery = true, value = "select * from orders o where o.date_created between :start and :end")
    List<Orders> getAllOrdersInMonth(@Param("start") Timestamp start, @Param("end") Timestamp end);

    List<Orders> findByUsername(String username);

    List<Orders> findOneById(Integer id);

    Orders findMotById(Integer id);
}
