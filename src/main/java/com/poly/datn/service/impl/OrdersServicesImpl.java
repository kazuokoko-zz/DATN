package com.poly.datn.service.impl;

import com.poly.datn.dao.*;
import com.poly.datn.entity.*;
import com.poly.datn.utils.CheckRole;
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

    @Autowired
    CheckRole checkRole;

    @Override
    public OrdersVO getByIdAndUserName(Integer id, Principal principal) throws SecurityException, NullPointerException {
        if (principal == null) {
            return null;
        }
        Orders orders = ordersDAO.findByIdAndUsername(id, principal.getName()).orElseThrow(() -> new SecurityException("Not your order"));

        return getDetailOrders(orders);
    }

    //, OrderDetailsVO orderDetailsVO, CustomerVO customerVO
    @Override
    public OrdersVO newOrder(OrdersVO ordersVO, Principal principal) {
        // save customer
        Customer customer = new Customer();
        BeanUtils.copyProperties(ordersVO.getCustomer(), customer);
        customer = customerDAO.save(customer);
        String changeBy = principal != null ? principal.getName() : "guest";
        //save order
        Orders orders = new Orders();
        orders.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        orders.setUsername(changeBy);
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
        OrderManagement orderManagement = new OrderManagement();
        orderManagement.setOrderId(orders.getId());
        orderManagement.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
        orderManagement.setChangedBy(changeBy);
        orderManagement.setStatus("Chờ xác nhận");
        orderManagementDAO.save(orderManagement);

        BeanUtils.copyProperties(orders, ordersVO);
        return ordersVO;

    }

    @Override
    public OrdersVO getByIdAndUserNameAdmin(Integer id, Principal principal) {
        if (!(checkRole.isHavePermition(principal.getName(), "Director") || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }

        Orders orders = ordersDAO.findById(id).orElseThrow(() -> new SecurityException("Not found"));
        return getDetailOrders(orders);
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

    private OrdersVO getDetailOrders(Orders orders) {
        Customer customer = customerDAO.findById(orders.getCustomerId()).orElseThrow(() -> new NullPointerException("Cannot find customer"));
        OrdersVO ordersVO = new OrdersVO();
        CustomerVO vo = new CustomerVO();
        BeanUtils.copyProperties(customer, vo);
        BeanUtils.copyProperties(orders, ordersVO);
        ordersVO.setCustomer(vo);
        List<OrderDetailsVO> orderDetailsVOS = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsDAO.findAllByOrderIdEquals(orders.getId())) {
            OrderDetailsVO orderDetailsVO = new OrderDetailsVO();
            BeanUtils.copyProperties(orderDetails, orderDetailsVO);
            orderDetailsVOS.add(orderDetailsVO);
        }
        ordersVO.setOrderDetails(orderDetailsVOS);
        return ordersVO;
    }
}
