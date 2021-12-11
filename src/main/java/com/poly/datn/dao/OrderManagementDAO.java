package com.poly.datn.dao;

import com.poly.datn.entity.OrderManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


}
