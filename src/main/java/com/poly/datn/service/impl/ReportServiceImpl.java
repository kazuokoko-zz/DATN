package com.poly.datn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.datn.dao.OrderManagementDAO;
import com.poly.datn.dao.OrdersDAO;
import com.poly.datn.entity.Orders;
import com.poly.datn.service.AutoTask.AutoTaskService;
import com.poly.datn.service.ReportService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.VoBoSung.OrderDTO.OrdersVO;
import com.poly.datn.vo.ProductVO;
import com.poly.datn.vo.TrendingVO;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.time.*;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    CheckRole checkRole;

    @Autowired
    OrderManagementDAO orderManagementDAO;

    @Autowired
    OrdersDAO ordersDAO;

    @Autowired
    ObjectMapper mapper;

    private Timestamp startTime;
    private Timestamp endTime;

    public void getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String month = String.valueOf(localDateTime.getMonth().getValue());
        String year = String.valueOf(localDateTime.getYear());

        YearMonth yearMonth = YearMonth.of(Integer.valueOf(year), Integer.valueOf(month));
        LocalDate firstOfMonth = yearMonth.atDay(1);
        startTime = Timestamp.valueOf(firstOfMonth.atStartOfDay());


        LocalDate last = yearMonth.atEndOfMonth();
        endTime = Timestamp.valueOf(last.atTime(23, 59, 59));
    }


    @Override
    public List<OrdersVO> getListUnComfirmOrders(Principal principal) {
        checjkPrincipal(principal);
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

    @Override
    public List<OrdersVO> getlistOrders(Principal principal) {
        checjkPrincipal(principal);
        getTime();
        List<OrdersVO> ordersVOS = new ArrayList<>();
        List<Orders> orders = ordersDAO.getAllOrdersInMonth(startTime, endTime);
        for (Orders order : orders) {
            OrdersVO ordersVO = new OrdersVO();
            BeanUtils.copyProperties(order, ordersVO);
            ordersVOS.add(ordersVO);
        }
        return ordersVOS;
    }

    @Override
    public List<OrdersVO> getListCancerOrders(Principal principal) {
        checjkPrincipal(principal);
        getTime();
        List<OrdersVO> ordersVOS = new ArrayList<>();
        List<Integer> ids = orderManagementDAO.getIdOfLastStatusInMonth("Đã hủy", startTime, endTime);
        List<Orders> orders = ordersDAO.getOrdersOfIds(ids);
        for (Orders order : orders) {
            OrdersVO ordersVO = new OrdersVO();
            BeanUtils.copyProperties(order, ordersVO);
            ordersVOS.add(ordersVO);
        }
        return ordersVOS;
    }

    @Override
    public List<OrdersVO> getListSuccessOrders(Principal principal) {
        checjkPrincipal(principal);
        getTime();
        List<OrdersVO> ordersVOS = new ArrayList<>();
        List<Integer> ids = orderManagementDAO.getIdOfLastStatusInMonth("Giao hàng thành công", startTime, endTime);
        List<Orders> orders = ordersDAO.getOrdersOfIds(ids);
        for (Orders order : orders
        ) {
            OrdersVO ordersVO = new OrdersVO();
            BeanUtils.copyProperties(order, ordersVO);
            ordersVOS.add(ordersVO);
        }
        return ordersVOS;
    }

    @Override
    public List<OrdersVO> getListComfimOrders(Principal principal) {
        checjkPrincipal(principal);
        getTime();
        List<OrdersVO> ordersVOS = new ArrayList<>();
        List<Integer> ids = orderManagementDAO.getIdOfLastStatus("Đã xác nhận");
        List<Orders> orders = ordersDAO.getOrdersOfIds(ids);
        for (Orders order : orders
        ) {
            OrdersVO ordersVO = new OrdersVO();
            BeanUtils.copyProperties(order, ordersVO);
            ordersVOS.add(ordersVO);
        }
        return ordersVOS;
    }


    @Override
    public List<OrdersVO> getListErrorOrders(Principal principal) {
        checjkPrincipal(principal);
        getTime();
        List<OrdersVO> ordersVOS = new ArrayList<>();
        List<Integer> ids = orderManagementDAO.getIdOfLastStatus("Đơn hàng lỗi");
        List<Orders> orders = ordersDAO.getOrdersOfIds(ids);
        for (Orders order : orders
        ) {
            OrdersVO ordersVO = new OrdersVO();
            BeanUtils.copyProperties(order, ordersVO);
            ordersVOS.add(ordersVO);
        }
        return ordersVOS;
    }

    @Override
    public Integer getNumberOfUnConfirmOrder(Principal principal) {
        checjkPrincipal(principal);
        return getListUnComfirmOrders(principal).size();
    }

    @Override
    public Integer sumOrderInMonth(Principal principal) {
        checjkPrincipal(principal);
        getTime();
        return ordersDAO.getAllOrdersInMonth(startTime, endTime).size();
    }

    @Override
    public Integer sumCancerOrderInMonth(Principal principal) {
        checjkPrincipal(principal);
        getTime();
        return orderManagementDAO.getIdOfLastStatusInMonth("Đã hủy", startTime, endTime).size();
    }

    @Override
    public Integer sumSuccessOrderInMonth(Principal principal) {
        checjkPrincipal(principal);
        getTime();
        return orderManagementDAO.getIdOfLastStatusInMonth("Giao hàng thành công", startTime, endTime).size();
    }

    @Override
    public Integer sumComfimOrder(Principal principal) {
        checjkPrincipal(principal);
        return orderManagementDAO.getIdOfLastStatus("Đã xác nhận").size();
    }

    @Override
    public Integer sumErrorOrder(Principal principal) {
        checjkPrincipal(principal);
        return orderManagementDAO.getIdOfLastStatus("Đơn hàng lỗi").size();
    }


    @Override
    public List<ProductVO> getTrendingAdmin(Principal principal) {
        checjkPrincipal(principal);
        List<ProductVO> productVOS = new ArrayList<>();
        for (TrendingVO trendingVO : AutoTaskService.trending) {
            productVOS.add(trendingVO.getProductVO());
        }

        return productVOS;
    }


    public void checjkPrincipal(Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
        }
    }

}
