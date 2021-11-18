package com.poly.datn.service.impl;

import com.poly.datn.dao.*;
import com.poly.datn.entity.*;
import com.poly.datn.vo.*;
import com.poly.datn.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrdersServicesImpl implements OrdersService {
    @Autowired
    OrdersDAO ordersDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    OrderDetailsDAO orderDetailsDAO;
    @Autowired
    OrderManagementDAO orderManagementDAO;

    @Autowired
    WarrantyDAO warrantyDAO;

    @Override
    public OrdersVO getByIdAndUserName(Integer id, String username) throws SecurityException, NullPointerException {
        Orders orders = ordersDAO.findByIdAndUsername(id, username).orElseThrow(() -> new SecurityException("Not your order"));

        Customer customer = customerDAO.findById(orders.getCustomerId()).orElseThrow(() -> new NullPointerException("Cannot find customer"));
        OrdersVO ordersVO = new OrdersVO();
        CustomerVO vo = new CustomerVO();
        BeanUtils.copyProperties(customer, vo);
        BeanUtils.copyProperties(orders, ordersVO);
        ordersVO.setCustomer(vo);
        List<OrderDetailsVO> orderDetailsVOS = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsDAO.findAllByOrderIdEquals(id)) {
            OrderDetailsVO orderDetailsVO = new OrderDetailsVO();
            BeanUtils.copyProperties(orderDetails, orderDetailsVO);
            orderDetailsVOS.add(orderDetailsVO);
        }
        ordersVO.setOrderDetails(orderDetailsVOS);
        return ordersVO;
    }

    //, OrderDetailsVO orderDetailsVO, CustomerVO customerVO
    @Override
    public OrdersVO newOrder(OrdersVO ordersVO, Principal principal) {
        // save customer
        Customer customer = new Customer();
        BeanUtils.copyProperties(ordersVO.getCustomer(), customer);
        customer = customerDAO.save(customer);

        //save order
        Orders orders = new Orders();
        orders.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        orders.setUsername(principal != null ? principal.getName() : "");
        orders.setCustomerId(customer.getId());
        Long totalPrice = 0L;
        List<OrderDetailsVO> orderDetailsVO = ordersVO.getOrderDetails();
        for (OrderDetailsVO orderDetailsVO1 : orderDetailsVO) {
            totalPrice += orderDetailsVO1.getPrice();
        }
        ;
        orders.setSumprice(totalPrice);
        orders = ordersDAO.save(orders);

        // save order detail
        List<OrderDetailsVO> orderDetailsVOS = ordersVO.getOrderDetails();
        for (OrderDetailsVO orderDetailsVO1 : orderDetailsVOS) {
            OrderDetails orderDetails = new OrderDetails();
            BeanUtils.copyProperties(orderDetailsVO1, orderDetails);
            orderDetails.setOrderId(orders.getId());
            orderDetailsDAO.save(orderDetails);
        }
        ;
        //save ordermanagement
        OrderManagementVO orderManagementVO = new OrderManagementVO();
        OrderManagement orderManagement = new OrderManagement();
        BeanUtils.copyProperties(orderManagementVO, orderManagement);
        orderManagementVO.setOrderId(orders.getId());
        orderManagementVO.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
        orderManagementVO.setChangedBy(principal != null ? principal.getName() : "");
        orderManagementVO.setStatus("Chờ xác nhận");
        orderManagement = orderManagementDAO.save(orderManagement);

        BeanUtils.copyProperties(orders, ordersVO);
        return ordersVO;

    }

    @Override
    public List<OrdersVO> getByUsername(Principal principal) {
        List<OrdersVO> ordersVOS = new ArrayList<>();
        ordersDAO.getByUsername(principal.getName()).forEach(orders -> {
            OrdersVO vo = new OrdersVO();
            BeanUtils.copyProperties(orders, vo);
            ordersVOS.add(vo);
        });
        return ordersVOS;
    }

    @Override
    public List<OrdersVO> getAll(Principal principal) {
        List<Orders> orders = ordersDAO.findAll();
        List<OrdersVO> ordersVOS = new ArrayList<>();
        orders.forEach(order -> {
            OrdersVO vo = new OrdersVO();
            BeanUtils.copyProperties(order, vo);

            //listcustomer
            Customer customer = customerDAO.findCustomerById(order.getCustomerId());
            if (customer == null) {

            } else {
                CustomerVO customerVO = new CustomerVO();
                BeanUtils.copyProperties(customer, customerVO);

                vo.setCustomer(customerVO);
            }
            //list ordermanager
            List<OrderManagement> orderManagement = orderManagementDAO.findByOrderId(order.getId());
            if (orderManagement.size() == 0) {

            } else {
                List<OrderManagementVO> orderManagements = new ArrayList<>();
                orderManagement.forEach(orderManagement1 -> {
                    OrderManagementVO orderManagementVO = new OrderManagementVO();
                    BeanUtils.copyProperties(orderManagement1, orderManagementVO);
                    orderManagements.add(orderManagementVO);
                });
                vo.setOrderManagements(orderManagements);
            }
            //list order detail
            List<OrderDetails> orderDetails = orderDetailsDAO.findAllByOrderIdEquals(order.getId());
            if (orderDetails.size() == 0) {

            } else {
                List<OrderDetailsVO> orderDetailsVOS = new ArrayList<>();
                orderDetails.forEach(orderDetails1 -> {
                    OrderDetailsVO orderDetailsVO = new OrderDetailsVO();
                    BeanUtils.copyProperties(orderDetails1, orderDetailsVO);
                    orderDetailsVOS.add(orderDetailsVO);
                });
                vo.setOrderDetails(orderDetailsVOS);
            }
            //list warranty
            List<Warranty> warranty = warrantyDAO.findByOrderId(order.getId());
            if (warranty.size() == 0) {

            } else {
                WarrantyVO warrantyVO = new WarrantyVO();
                BeanUtils.copyProperties(warranty, warrantyVO);
                vo.setWarranty(warrantyVO);
            }
            ordersVOS.add(vo);
        });
        return ordersVOS;
    }
}
