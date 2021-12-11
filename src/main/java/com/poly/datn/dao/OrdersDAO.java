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
    @Query(nativeQuery = true,value ="select * from orders c where" +
                     " c.id in(select order_id from warranty w where w.order_id =:order_id " +
                    "and w.product_id =:product_id)")
    Orders findOneByIdForWarranty(@Param("order_id") Integer order_id,@Param("product_id") Integer product_id);

//    @Query(nativeQuery = true,value = "select count(*) from Orders o where CONVERT(varchar,dateadd(d,-(day(getdate()-1)),getdate()),106)")
//    @Query(value = "select count(o) from Orders o where o.dateCreated.get")
    @Query(nativeQuery = true,value ="select count(*) from orders c where c.date_created between :startTime and  :EndTime")
    Integer countOrdersBy(@Param("startTime") Timestamp startTime,@Param("EndTime") Timestamp EndTime);

    @Query(nativeQuery = true,value ="select count(*) from orders c where c.id in(select order_id from ordermanagement o where o.status = \"Đã hủy\" )and c.date_created between :startTime and  :EndTime")
    Integer countCancerOrdersBy(@Param("startTime") Timestamp startTime,@Param("EndTime") Timestamp EndTime);

    @Query(nativeQuery = true,value ="select count(*) from orders c where c.id in(select order_id from ordermanagement o where o.status = \"Giao hàng thành công\" )and c.date_created between :startTime and  :EndTime")
    Integer countSuccessOrdersBy(@Param("startTime") Timestamp startTime,@Param("EndTime") Timestamp EndTime);

    @Query(nativeQuery = true,value ="select count(*) from orders c where c.id in(select order_id from ordermanagement o where o.status = \"Đã xác nhận\" )")
    Integer countComfimOrders();


    List<Orders> findByUsername(String username);
    List<Orders> findOneById(Integer id);
    Orders findMotById(Integer id);
}
