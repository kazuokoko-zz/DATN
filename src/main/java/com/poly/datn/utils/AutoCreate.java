package com.poly.datn.utils;

import com.poly.datn.entity.OrderManagement;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AutoCreate {
    public static OrderManagement createOrderManagement(Integer ordersId, String status, String changeBy, String note) {
        OrderManagement orderManagement = new OrderManagement();
        orderManagement.setOrderId(ordersId);
        orderManagement.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
        orderManagement.setChangedBy(changeBy);
        orderManagement.setStatus(status);
        orderManagement.setNote(note);
        return orderManagement;
    }
}
