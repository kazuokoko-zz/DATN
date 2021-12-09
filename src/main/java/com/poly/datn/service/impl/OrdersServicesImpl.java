package com.poly.datn.service.impl;

import com.poly.datn.dao.*;
import com.poly.datn.entity.*;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.utils.PriceUtils;
import com.poly.datn.utils.StringFind;
import com.poly.datn.vo.*;
import com.poly.datn.service.OrdersService;
import com.poly.datn.vo.VoBoSung.NoteOrderManagementVo;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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

    @Autowired
    PriceUtils priceUtils;

    @Autowired
    ProductSaleDAO productSaleDAO;

    @Autowired
    CartDetailDAO cartDetailDAO;

    @Autowired
    AccountDAO accountDAO;

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
        OrdersVO vo = managerOrderStatus(orders, changeBy, "Chờ xác nhận");
        if (principal != null) {
            cartDetailDAO.removeFromCartById(accountDAO.findAccountByUsername(principal.getName()).getId());
        }
        return vo;
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
    public boolean cancerOrder(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        if (principal == null) {
            return false;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return false;
        }
        Orders orders = ordersDAO.findMotById(id);
        if (orders == null) {
            throw new NotFoundException("api.error.API-003");
        }
        OrderManagement orderManagement = orderManagementDAO.findOneByOrderId(orders.getId());
        if (orderManagement.getStatus().equals("Đã hủy") || orderManagement.equals("Giao hàng thành công")) {
            throw new NotImplementedException("Không thể xác nhân đơn hàng đã hủy hoặc giao thành công");
        } else {
            orderManagement.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
            orderManagement.setChangedBy(principal.getName());
            orderManagement.setStatus("Đã hủy");
            if (noteOrderManagementVo.getNote() == null) {
                orderManagement.setNote("Thực hiện hủy đơn hàng");
            } else {
                orderManagement.setNote(noteOrderManagementVo.getNote());
            }
            orderManagementDAO.save(orderManagement);
            return true;
        }

    }

    @Override
    public boolean confimOrder(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        if (principal == null) {
            return false;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return false;
        }
        Orders orders = ordersDAO.findMotById(id);
        if (orders == null) {
            throw new NotFoundException("api.error.API-003");
        }
        OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
        if (orderManagement.getStatus().equals("Đã hủy") || orderManagement.equals("Giao hàng thành công")) {
            throw new NotImplementedException("Không thể xác nhân đơn hàng đã hủy hoặc giao thành công");
        } else {
            orderManagement.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
            orderManagement.setChangedBy(principal.getName());
            orderManagement.setStatus("Đã xác nhận");
            String note = "";
            if (noteOrderManagementVo.getNote().isBlank()) {
                OrderManagement last = orderManagementDAO.getLastManager(orders.getId());
                if (orders.getTypePayment().equals(Boolean.TRUE)) note = last.getNote();
            } else {
                orderManagement.setNote(noteOrderManagementVo.getNote());
            }
            orderManagement.setNote(note);
            orderManagementDAO.save(orderManagement);
            return true;
        }
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
            if (name.isPresent()) {
                nameok = checkName(customer, name.get());
            }
            if (email.isPresent()) {
                mailok = checkEmail(customer, email.get());
            }
            if (phone.isPresent()) {
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
        orders.setTypePayment(false);
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
            orderDetails.setDiscount(priceUtils.maxDiscountAtPresentOf(orderDetails.getProductId()));
            orderDetailsDAO.save(orderDetails);
            ProductSale productSale = priceUtils.getSaleHavingMaxDiscountOf(orderDetails.getProductId());
            if (productSale == null)
                continue;
            productSale.setQuantity(productSale.getQuantity() - orderDetails.getQuantity());
            productSaleDAO.save(productSale);
        }
    }

    private OrdersVO managerOrderStatus(Orders orders, String changeBy, String status) {
        //save ordermanagement

        String note = "";
        if (status == "Đã xác nhận") {
            if (orders.getTypePayment().equals(Boolean.TRUE)) note = "Đã thanh toán";
        } else {
            OrderManagement last = orderManagementDAO.getLastManager(orders.getId());
            if (orders.getTypePayment().equals(Boolean.TRUE)) note = last.getNote();
        }

        OrdersVO ordersVO = getDetailOrders(orders, status);
        OrderManagement orderManagement = new OrderManagement();
        orderManagement.setOrderId(orders.getId());
        orderManagement.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
        orderManagement.setChangedBy(changeBy);
        orderManagement.setNote(note);
        orderManagement.setStatus(status);
        orderManagementDAO.save(orderManagement);
        BeanUtils.copyProperties(orders, ordersVO);
        ordersVO.setStatus(status);
        return ordersVO;
    }

//    private void

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
