package com.poly.datn.dao;

import com.poly.datn.entity.OrderManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OrderManagementDAO extends JpaRepository<OrderManagement, Long> {
    List<OrderManagement> findByOrderId(Integer orderId);

    OrderManagement findOneByOrderId(Integer orderId);

    @Query(nativeQuery = true, value = "select  * from ordermanagement o where o.order_id = :id order by o.time_change desc limit 1")
    OrderManagement getLastManager(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "SELECT order_id  FROM ordermanagement m where (m.order_id , m.time_change) IN(\n" +
            "SELECT  order_id, MAX(time_change) AS  time_change FROM ordermanagement m \n" +
            "GROUP BY order_id ) AND `status` = :status")
    List<Integer> getIdOfLastStatus(@Param("status") String status);

    @Query(nativeQuery = true, value = "SELECT order_id  FROM ordermanagement m , orders o where (m.order_id , m.time_change) IN(\n" +
            "SELECT  order_id, MAX(time_change) AS  time_change FROM ordermanagement m \n" +
            "GROUP BY order_id ) AND `status` = :status AND o.id = m.order_id and o.date_created between :start and :end")
    List<Integer> getIdOfLastStatusInMonth(@Param("status") String status, @Param("start") Timestamp start, @Param("end") Timestamp end);


}
