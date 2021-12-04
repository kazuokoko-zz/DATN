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

//    @Query(value = "select m.orderId  from OrderManagement m where m.status = ?1")
//    List<Integer> getIdOfLastStatus(String status);


}
