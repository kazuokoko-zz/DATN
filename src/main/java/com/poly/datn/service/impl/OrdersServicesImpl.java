package com.poly.datn.service.impl;

import com.poly.datn.dao.*;
import com.poly.datn.entity.*;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.utils.StringFind;
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
import java.util.Optional;

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

    @Autowired
    ProductDAO productDAO;

    @Autowired
    StringFind stringFind;

    @Override
    public OrdersVO getByIdAndUserName(Integer id, Principal principal) throws SecurityException, NullPointerException {
        if (principal == null) {
            return null;
        }
        Orders orders = ordersDAO.findByIdAndUsername(id, principal.getName()).orElseThrow(() -> new SecurityException("Not your order"));

        return getDetailOrders(orders, "Chờ xác nhận");
    }

    //, OrderDetailsVO orderDetailsVO, CustomerVO customerVO
    @Override
    public OrdersVO newOrder(OrdersVO ordersVO, Principal principal) {

        String changeBy = principal != null ? principal.getName() : "guest";
        // save customer
        Orders orders = createOders(ordersVO, changeBy);
        saveDetails(orders, ordersVO);
        return managerOrderStatus(orders, changeBy, "Chờ xác nhận");
    }

    @Override
    public OrdersVO getByIdAndUserNameAdmin(Integer id, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }

        Orders orders = ordersDAO.findById(id).orElseThrow(() -> new SecurityException("Not found"));
        return getDetailOrders(orders, null);
    }

    @Override
    public OrdersVO newOrderAdmin(OrdersVO ordersVO, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        String changeBy = principal != null ? principal.getName() : "guest";
        Orders orders = createOders(ordersVO, changeBy);
        saveDetails(orders, ordersVO);
        return managerOrderStatus(orders, changeBy, "Đã xác nhận");
    }

    @Override
    public OrdersVO updateOrderAdmin(Optional<Integer> id, Optional<String> status, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff")) ||
                !id.isPresent() || !status.isPresent()) {
            return null;
        }
        Orders orders = ordersDAO.getById(id.get());
        return managerOrderStatus(orders, principal.getName(), status.get());
    }

    @Override
    public List<OrdersVO> getList(Principal principal, Optional<Integer> id, Optional<String> email, Optional<String> name, Optional<String> phone) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        List<Orders> orders = ordersDAO.findAll();
        List<OrdersVO> ordersVOS = new ArrayList<>();
        for (Orders order : orders) {
            Customer customer = customerDAO.findCustomerById(order.getCustomerId());
            Boolean idok = true;
            Boolean nameok = true;
            Boolean mailok = true;
            Boolean phoneok = true;
            if (id.isPresent()) {
                idok = id.get() == order.getId();
            }
            if (id.isPresent()) {
                nameok = checkName(customer, name.get());
            }
            if (id.isPresent()) {
                mailok = checkEmail(customer, email.get());
            }
            if (id.isPresent()) {
                phoneok = checkPhone(customer, phone.get());
            }
            if (idok || nameok || mailok || phoneok) {
                OrdersVO ordersVO = new OrdersVO();
                BeanUtils.copyProperties(order, ordersVO);
                ordersVO.setStatus(getStatus(order.getId()));
                ordersVOS.add(ordersVO);
            }
        }
        return ordersVOS;
    }

    @Override
    public List<OrdersVO> getByUsername(Principal principal) {
        List<OrdersVO> ordersVOS = new ArrayList<>();
        for (Orders orders : ordersDAO.getByUsername(principal.getName())) {
            OrdersVO vo = new OrdersVO();
            BeanUtils.copyProperties(orders, vo);
            vo.setStatus(getStatus(orders.getId()));
            ordersVOS.add(vo);
        }
        return ordersVOS;
    }

    @Override
    public List<OrdersVO> getAll(Principal principal) {
        List<Orders> orders = ordersDAO.findAll();
        List<OrdersVO> ordersVOS = new ArrayList<>();
        for (Orders order : orders) {
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
                for (OrderDetails orderDetails1 : orderDetails) {
                    OrderDetailsVO orderDetailsVO = new OrderDetailsVO();
                    BeanUtils.copyProperties(orderDetails1, orderDetailsVO);
                    orderDetailsVO.setProductName(productDAO.getById(orderDetails1.getProductId()).getName());
                    orderDetailsVOS.add(orderDetailsVO);
                }
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
            vo.setStatus(getStatus(order.getId()));
            ordersVOS.add(vo);
        }
        return ordersVOS;
    }

    private OrdersVO getDetailOrders(Orders orders, String status) {
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
            orderDetailsVO.setProductName(productDAO.getById(orderDetails.getProductId()).getName());
            orderDetailsVOS.add(orderDetailsVO);
        }
        ordersVO.setOrderDetails(orderDetailsVOS);
        ordersVO.setStatus(status != null ? status : getStatus(orders.getId()));
        return ordersVO;
    }

    private Orders createOders(OrdersVO ordersVO, String changeBy) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(ordersVO.getCustomer(), customer);
        customer = customerDAO.save(customer);
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
        orders.setSumprice(totalPrice);
        return ordersDAO.save(orders);
    }

    private void saveDetails(Orders orders, OrdersVO ordersVO) {
        // save order detail
        List<OrderDetailsVO> orderDetailsVOS = ordersVO.getOrderDetails();
        for (OrderDetailsVO orderDetailsVO1 : orderDetailsVOS) {
            OrderDetails orderDetails = new OrderDetails();
            BeanUtils.copyProperties(orderDetailsVO1, orderDetails);
            orderDetails.setOrderId(orders.getId());
            orderDetailsDAO.save(orderDetails);
        }
    }

    private OrdersVO managerOrderStatus(Orders orders, String changeBy, String status) {
        //save ordermanagement
        OrdersVO ordersVO = getDetailOrders(orders, status);
        OrderManagement orderManagement = new OrderManagement();
        orderManagement.setOrderId(orders.getId());
        orderManagement.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
        orderManagement.setChangedBy(changeBy);
        orderManagement.setStatus(status);
        orderManagementDAO.save(orderManagement);
        BeanUtils.copyProperties(orders, ordersVO);
        ordersVO.setStatus(status);
        return ordersVO;
    }

    boolean checkName(Customer customer, String name) {
        return stringFind.checkContains(customer.getFullname(), name);
    }

    boolean checkEmail(Customer customer, String email) {
        return customer.getEmail().equalsIgnoreCase(email);
    }

    boolean checkPhone(Customer customer, String phone) {
        return customer.getPhone().equalsIgnoreCase(phone);
    }

    private String getStatus(Integer id) {
        OrderManagement orderManagement = orderManagementDAO.getLastManager(id);
        return orderManagement.getStatus();
    }
}
