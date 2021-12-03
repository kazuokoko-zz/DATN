package com.poly.datn.service.impl;

import com.poly.datn.dao.OrderManagementDAO;
import com.poly.datn.dao.OrdersDAO;
import com.poly.datn.entity.Orders;
import com.poly.datn.service.ReportService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.OrdersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    CheckRole checkRole;

    @Autowired
    OrderManagementDAO orderManagementDAO;

    @Autowired
    OrdersDAO ordersDAO;

    @Override
    public List<OrdersVO> getListUnComfirmOrders(Principal principal) {
        if (principal == null)
            return null;
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        List<OrdersVO> ordersVOS = new ArrayList<>();
        for (Integer id : orderManagementDAO.getIdOfLastStatus("Chờ xác nhận")) {
            OrdersVO ordersVO = new OrdersVO();
            Orders orders = ordersDAO.getById(id);
            if (orders == null)
                continue;
            BeanUtils.copyProperties(orders, ordersVO);
            ordersVOS.add(ordersVO);
        }
        return ordersVOS;
    }
}
