package com.poly.datn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.datn.dao.OrderManagementDAO;
import com.poly.datn.dao.OrdersDAO;
import com.poly.datn.entity.Orders;
import com.poly.datn.service.AutoTask.AutoTaskService;
import com.poly.datn.service.ReportService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.OrdersVO;
import com.poly.datn.vo.ProductVO;
import com.poly.datn.vo.TrendingVO;
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

    @Override
    public Integer getNumberOfUnConfirmOrder(Principal principal) {
        return getListUnComfirmOrders(principal).size();
    }

    @Override
    public Integer sumOrderInMonth(Principal principal) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String month = String.valueOf(localDateTime.getMonth().getValue());
        String year = String.valueOf(localDateTime.getYear());

        YearMonth yearMonth = YearMonth.of(Integer.valueOf(year), Integer.valueOf(month));
        LocalDate firstOfMonth = yearMonth.atDay(1);
        Timestamp startTime = Timestamp.valueOf(firstOfMonth.atStartOfDay());


        LocalDate last = yearMonth.atEndOfMonth();
        Timestamp endTime = Timestamp.valueOf(last.atTime(23, 59, 59));

//        Timestamp ts = Timestamp.valueOf(LocalDate.now().atStartOfDay());

        return ordersDAO.countOrdersBy(startTime, endTime);
    }

    @Override
    public Integer sumCancerOrderInMonth(Principal principal) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String month = String.valueOf(localDateTime.getMonth().getValue());
        String year = String.valueOf(localDateTime.getYear());

        YearMonth yearMonth = YearMonth.of(Integer.valueOf(year), Integer.valueOf(month));
        LocalDate firstOfMonth = yearMonth.atDay(1);
        Timestamp startTime = Timestamp.valueOf(firstOfMonth.atStartOfDay());


        LocalDate last = yearMonth.atEndOfMonth();
        Timestamp endTime = Timestamp.valueOf(last.atTime(23, 59, 59));

//        Timestamp ts = Timestamp.valueOf(LocalDate.now().atStartOfDay());

        return ordersDAO.countCancerOrdersBy(startTime, endTime);
    }

    @Override
    public Integer sumSuccessOrderInMonth(Principal principal) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String month = String.valueOf(localDateTime.getMonth().getValue());
        String year = String.valueOf(localDateTime.getYear());

        YearMonth yearMonth = YearMonth.of(Integer.valueOf(year), Integer.valueOf(month));
        LocalDate firstOfMonth = yearMonth.atDay(1);
        Timestamp startTime = Timestamp.valueOf(firstOfMonth.atStartOfDay());


        LocalDate last = yearMonth.atEndOfMonth();
        Timestamp endTime = Timestamp.valueOf(last.atTime(23, 59, 59));

//        Timestamp ts = Timestamp.valueOf(LocalDate.now().atStartOfDay());

        return ordersDAO.countSuccessOrdersBy(startTime, endTime);
    }


    @Override
    public List<ProductVO> getTrendingAdmin(Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!checkRole.isHavePermition(principal.getName(), "Director")
                && !checkRole.isHavePermition(principal.getName(), "Staff"))
            return null;
        List<ProductVO> productVOS = new ArrayList<>();
        for (TrendingVO trendingVO : AutoTaskService.trending) {
            productVOS.add(trendingVO.getProductVO());
        }

        return productVOS;
    }

    @Override
    public List<OrdersVO> getListCancerOrderInMonth(Principal principal) {
        return null;
    }

    @Override
    public List<OrdersVO> getListComfimOrderInMonth(Principal principal) {
        return null;
    }


}
