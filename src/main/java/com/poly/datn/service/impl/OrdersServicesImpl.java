package com.poly.datn.service.impl;

import com.poly.datn.dao.*;
import com.poly.datn.entity.*;
import com.poly.datn.service.OrdersService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.utils.PriceUtils;
import com.poly.datn.utils.StringFind;
import com.poly.datn.vo.*;
import com.poly.datn.vo.VoBoSung.NoteOrderManagementVo;
import com.poly.datn.vo.VoBoSung.OrderDTO.OrdersVO;
import com.poly.datn.vo.VoBoSung.ShowProductWarrantyVO;
import com.poly.datn.vo.mailSender.InfoSendOrder;
import com.poly.datn.vo.mailSender.InfoSendStatusOrder;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    SendMail sendMail;

    @Autowired
    ProductColorDAO productColorDAO;

    private List<InfoSendStatusOrder> infoSendStatusOrder = new ArrayList<>();

    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    public void taskSendMailStatus(){
        try {
                if(infoSendStatusOrder.size() > 0) {
                for (InfoSendStatusOrder infoSendStatusOrder2 : infoSendStatusOrder
                ) {
                    sendMail.sentMailStatusOrder(infoSendStatusOrder2);
                }
                infoSendStatusOrder.clear();
            } else {
                return;
            }
        }catch (Exception exception){
            throw  new RuntimeException(exception);
        }
    }

    private InfoSendStatusOrder sendMailUpdateStatus(OrderManagement orderManagement){
        Orders orders = ordersDAO.findMotById(orderManagement.getOrderId());
        Customer customer = customerDAO.findCustomerById(orders.getCustomerId());
        InfoSendStatusOrder infoSendStatusOrder3 = new InfoSendStatusOrder();
        infoSendStatusOrder3.setName(customer.getFullname());
        infoSendStatusOrder3.setPhone(customer.getPhone());
        infoSendStatusOrder3.setEmail(customer.getEmail());
        infoSendStatusOrder3.setAddress(customer.getAddress());
        infoSendStatusOrder3.setOrderId(orders.getId());
        List<OrderManagement> orderManagement1 = orderManagementDAO.findByOrderId(orders.getId());
        List<OrderManagementVO> orderManagementVOS = new ArrayList<>();
        for (OrderManagement orderManagement2:  orderManagement1 ) {
            OrderManagementVO orderManagementVO = new OrderManagementVO();
            BeanUtils.copyProperties(orderManagement2,orderManagementVO );
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            orderManagementVO.setTimeChange(sdf.format(new Date(orderManagement2.getTimeChange().getTime())));
            orderManagementVOS.add(orderManagementVO);
        }
        OrderManagementVO[] voss = new OrderManagementVO[orderManagement1.size()];
        orderManagementVOS.stream().collect(Collectors.toList()).toArray(voss);
        infoSendStatusOrder3.setOrderManagementVO(voss);
        return infoSendStatusOrder3;
    }



    @Override
    public OrdersVO getByIdAndUserName(Integer id, Principal principal) throws SecurityException, NullPointerException {
        if (principal == null) {
            return null;
        }
        Orders orders = ordersDAO.findByIdAndUsername(id, principal.getName()).orElseThrow(() -> new SecurityException("Not your order"));

        OrdersVO vo = getDetailOrders(orders, null);

        return getStatusLine(vo);
    }


    //, OrderDetailsVO orderDetailsVO, CustomerVO customerVO
    @Override
    public OrdersVO newOrder(NewOrdersVO ordersVO, Principal principal) {

        String changeBy = principal != null ? principal.getName() : "guest";
        if (ordersVO.getOrderDetails() == null) {
            throw new NotImplementedException("Không có sản phẩm trong hóa đơn");
        }
        // save customer
        Orders orders = createOders(ordersVO, changeBy);

        saveDetails(orders, ordersVO);
        OrdersVO vo = managerOrderStatus(orders, changeBy, "Chờ xác nhận");
        if (principal != null) {
            List<CartDetail> details = cartDetailDAO.getCartDetailsByUsername(principal.getName());
            for (CartDetail detail : details) {
                cartDetailDAO.deleteById(detail.getId());
            }
        }
        if (ordersVO.getCustomer().getEmail() == null) {
           throw new NotImplementedException("Email không hợp lệ");
        }

        List<OrderDetailsVO> vos = vo.getOrderDetails();
        InfoSendOrder infoSendOrder = new InfoSendOrder();

        Long totalPrice = 0L;
        Long totalDiscount = 0L;
        Long price = 0L;
        for (OrderDetailsVO detailsVO : vos) {
            if (detailsVO.getQuantity() <= 0) {
              throw new NotImplementedException("Số lượng sản phẩm tên: "+ detailsVO.getProductName() + " đang không hợp lệ( <1), vui lòng kiểm tra lại");
            } else {
                price += detailsVO.getQuantity() * detailsVO.getPrice();
                Long discount = detailsVO.getQuantity() * detailsVO.getDiscount();
                totalDiscount += discount;
                totalPrice += detailsVO.getQuantity() * (detailsVO.getPrice() - detailsVO.getDiscount());
            }
        }
/**
 * send order mail
 */
        infoSendOrder.setDiscount(totalDiscount);
        infoSendOrder.setTotalPrice(totalPrice);
        infoSendOrder.setPrice(price);
        infoSendOrder.setName(ordersVO.getCustomer().getFullname());
        infoSendOrder.setAddress(ordersVO.getCustomer().getAddress());
        infoSendOrder.setEmail(ordersVO.getCustomer().getEmail());
        infoSendOrder.setPhone(ordersVO.getCustomer().getPhone());
        OrderDetailsVO[] voss = new OrderDetailsVO[vos.size()];
        vos.stream().map(
                detail -> {
                    Product product = productDAO.getById(detail.getProductId());
                    detail.setWarranty(product.getWarranty());
                    return detail;
                }
        ).collect(Collectors.toList()).toArray(voss);
        infoSendOrder.setOrderDetails(voss);
        sendMail.sentMailOrder(infoSendOrder);
        return vo;
    }

    @Override
    public OrdersVO getByIdAndUserNameAdmin(Integer id, Principal principal) {
        checkPrincipal(principal);
        Orders orders = ordersDAO.findById(id).orElseThrow(() -> new SecurityException("Not found"));
        OrdersVO vo = getDetailOrders(orders, null);
        return getStatusLine(vo);
    }

    public Integer updateQuantityForProductBeffoCancerOrder(Integer id) {
        Integer countQuantity = 0;
        List<OrderDetails> orderDetails = orderDetailsDAO.findAllByOrderIdEquals(id);
        for (OrderDetails orderDetails1 : orderDetails
        ) {
            ProductColor productColor = productColorDAO.findByProductIdAndColorId(orderDetails1.getProductId(), orderDetails1.getColorId());
            productColor.setQuantity(orderDetails1.getQuantity() + productColor.getQuantity());
            productColorDAO.save(productColor);
        }
        return countQuantity;
    }

    public void updateStatus(String status, String note, Integer id, Orders orders, NoteOrderManagementVo noteOrderManagementVo, String username) {
        try {
            OrderManagement orderManagement1 = new OrderManagement();
            orderManagement1.setOrderId(orders.getId());
            orderManagement1.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
            orderManagement1.setChangedBy(username);
            orderManagement1.setStatus(status);
            if (noteOrderManagementVo.getNote() == "") {
                orderManagement1.setNote(note);
            } else {
                orderManagement1.setNote(noteOrderManagementVo.getNote());
            }
            orderManagement1 =orderManagementDAO.save(orderManagement1);
            InfoSendStatusOrder infoSendStatusOrder1 =sendMailUpdateStatus( orderManagement1);
            infoSendStatusOrder.add(infoSendStatusOrder1);
        } catch (Exception e) {
            throw new NotImplementedException("Có lỗi khi thay đổi trạng thái đơn hàng");
        }
    }

    public void checkPrincipal(Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
        }
    }
    public Orders checkOrderAdmin(Integer id, Principal principal){
        checkPrincipal(principal);
        Orders orders = ordersDAO.findMotById(id);
        if (orders == null) {
            throw new NotImplementedException("Không tồn tại đơn hàng");
        }
        return orders;
    }

    @Override
    public boolean cancerOrder(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        Orders orders =  checkOrderAdmin( id,  principal);
        OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
        if (orderManagement.getStatus().equals("Chờ xác nhận") || orderManagement.getStatus().equals("Đã xác nhận") || orderManagement.getStatus().equals("Yêu cầu hủy")) {
            String status = "Đã hủy";
            String note = "Thực hiện hủy đơn hàng";
            updateStatus(status, note, id, orders, noteOrderManagementVo, principal.getName());
            updateQuantityForProductBeffoCancerOrder(id);
            return true;
        } else {
            throw new NotImplementedException("Không thể xác nhân đơn hàng đã hủy hoặc giao thành công");
        }
    }

    @Override
    public boolean confimOrder(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        Orders orders =  checkOrderAdmin( id,  principal);
        OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
        if (orderManagement.getStatus().equals("Chờ xác nhận")) {
            String status = "Đã xác nhận";
            String note = "Thực hiện xác nhận đơn hàng";
            updateStatus(status, note, id, orders, noteOrderManagementVo, principal.getName());
            return true;
        } else {
            throw new NotImplementedException("Không thể xác nhân đơn hàng đã hủy hoặc giao thành công");
        }
    }

    @Override
    public boolean confimTransport(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        Orders orders =  checkOrderAdmin( id,  principal);
        OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
        if (orderManagement.getStatus().equals("Đã xác nhận")) {
            String status = "Đang giao hàng";
            String note = "Thực hiện xác nhận đơn hàng đang được giao";
            updateStatus(status, note, id, orders, noteOrderManagementVo, principal.getName());
            return true;
        } else {
            throw new NotImplementedException("Không thể xác nhân giao hàng đơn hàng chưa được xác nhận");
        }
    }

    @Override
    public boolean requestReturns(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        Orders orders =  checkOrderAdmin( id,  principal);
        OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
        if (orderManagement.getStatus().equals("Đang giao hàng")) {
            String status = "Đang hoàn hàng";
            String note = "Thực hiện yêu cầu hoàn trả đơn hàng";
            updateStatus(status, note, id, orders, noteOrderManagementVo, principal.getName());
            return true;
        } else {
            throw new NotImplementedException("Không thể trả hàng, hãy chọn hủy đơn");
        }
    }

    @Override
    public boolean comfimReturns(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        Orders orders =  checkOrderAdmin( id,  principal);
        OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
        if (orderManagement.getStatus().equals("Đang hoàn hàng") || orderManagement.getStatus().equals("Yêu cầu trả hàng")) {
            String status = "Đã nhận lại hàng hoàn về";
            String note = "Thực hiện xác nhận hoàn trả đơn hàng";
            updateStatus(status, note, id, orders, noteOrderManagementVo, principal.getName());
            updateQuantityForProductBeffoCancerOrder(id);
            return true;
        } else {
            throw new NotImplementedException("Không thể trả hàng đơn hàng này");
        }
    }


    @Override
    public boolean confimSell(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        Orders orders =  checkOrderAdmin( id,  principal);
        OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
        if (orderManagement.getStatus().equals("Đang giao hàng")) {
            String status = "Giao hàng thành công";
            String note = "Thực hiện giao hàng thành công";
            updateStatus(status, note, id, orders, noteOrderManagementVo, principal.getName());
            return true;
        } else {
            throw new NotImplementedException("Không thể trả hàng, hãy chọn hủy đơn");
        }
    }



    public void requestStatusUser(String status, String note, Integer id, Orders orders, NoteOrderManagementVo noteOrderManagementVo, String username) {
        try {
            OrderManagement orderManagement1 = new OrderManagement();
            orderManagement1.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
            orderManagement1.setChangedBy(username);
            orderManagement1.setOrderId(orders.getId());
            orderManagement1.setStatus(status);
            orderManagement1.setNote(note);
            orderManagement1 =  orderManagementDAO.save(orderManagement1);
            InfoSendStatusOrder infoSendStatusOrder1 =sendMailUpdateStatus( orderManagement1);
            infoSendStatusOrder.add(infoSendStatusOrder1);
        } catch (Exception e) {
            throw new NotImplementedException("Có lỗi khi thay đổi trạng thái đơn hàng");
        }

    }
    public Orders checkUser(Integer id, Principal principal){
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        Orders orders = ordersDAO.findMotById(id);
        if (orders == null) {
            throw new NotImplementedException("Không tồn tại đơn hàng");
        }
        if (!orders.getUsername().equals(principal.getName())) {
            throw new NotImplementedException("Không có quyền cập nhập đơn hàng này");
        }
        return orders;
    }
    @Override
    public boolean cancerOrderUser(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
            Orders orders = checkUser(id,  principal);
            OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
            if (orderManagement.getStatus().equals("Chờ xác nhận")) {
                String status = "Đã hủy";
                String note;
                if (noteOrderManagementVo.getNote() == "") {
                    note = "Thực hiện hủy đơn hàng do người dùng yêu cầu";
                } else {
                    note = "Thực hiện hủy đơn hàng do người dùng yêu cầu, lý do: " + noteOrderManagementVo.getNote();
                }
                requestStatusUser(status, note, id, orders, noteOrderManagementVo, principal.getName());
                updateQuantityForProductBeffoCancerOrder(id);
                return true;
            } else {
                throw new NotImplementedException("Không thể cập nhập đơn hàng này");
            }
    }

    @Override
    public boolean requestCancerOrderUser(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        Orders orders = checkUser( id,  principal);
            OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
            if (orderManagement.getStatus().equals("Đã xác nhận")) {
                String status = "Yêu cầu hủy";
                String note;
                if (noteOrderManagementVo.getNote() == "") {
                    note = "Yêu cầu hủy đơn hàng do người dùng yêu cầu";
                } else {
                    note = "Yêu cầu hủy đơn hàng do người dùng yêu cầu, lý do: " + noteOrderManagementVo.getNote();
                }
                requestStatusUser(status, note, id, orders, noteOrderManagementVo, principal.getName());
                return true;
            } else {
                throw new NotImplementedException("Không thể cập nhập đơn hàng này");
            }
    }
//    @Override
//    public boolean requestModifyOrderUser(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
//        Orders orders = checkUser( id,  principal);
//            OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
//            if (orderManagement.getStatus().equals("Chờ xác nhận")) {
//                String status = "Yêu cầu sửa";
//                String note;
//                if (noteOrderManagementVo.getNote() == "") {
//                    note = "Yêu cầu sửa đơn hàng do người dùng yêu cầu, nhân viên sẽ liên hệ lại để xác nhận";
//                } else {
//                    note = "Yêu cầu sửa đơn hàng do người dùng yêu cầu, lý do: " + noteOrderManagementVo.getNote();
//                }
//                requestStatusUser(status, note, id, orders, noteOrderManagementVo, principal.getName());
//                return true;
//            } else {
//                throw new NotImplementedException("Không thể cập nhập đơn hàng này");
//            }
//    }

    @Override
    public boolean confimReturnsUser(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        Orders orders = checkUser( id,  principal);
            OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
            if (orderManagement.getStatus().equals("Giao hàng thành công")) {
                String status = "Yêu cầu trả hàng";
                String note;
                if (noteOrderManagementVo.getNote() == "") {
                    note = "Yêu cầu trả hàng do người dùng yêu cầu";
                } else {
                    note = "Yêu cầu trả hàng do người dùng yêu cầu, lý do:  " + noteOrderManagementVo.getNote();
                }
                requestStatusUser(status, note, id, orders, noteOrderManagementVo, principal.getName());
                return true;
            } else {
                throw new NotImplementedException("Không thể cập nhập đơn hàng này");
            }
    }

    @Override
    public boolean updateNoteOrderManagement(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal) {
        checkPrincipal(principal);
        Orders orders = ordersDAO.findMotById(id);
        if (orders == null) {
            throw new NotFoundException("api.error.API-003");
        }
        OrderManagement orderManagement = orderManagementDAO.getLastManager(orders.getId());
        if (orderManagement.getStatus().equals("Đã hủy") || orderManagement.equals("Giao hàng thành công") || orderManagement.equals("Đơn hàng lỗi")) {
            throw new NotImplementedException("Không thể cập nhập đơn hàng này");
        } else {
            if (noteOrderManagementVo.getNote() == "") {
                return false;
            } else {
                OrderManagement orderManagement1 = new OrderManagement();
                orderManagement1.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
                orderManagement1.setChangedBy(principal.getName());
                orderManagement1.setOrderId(orders.getId());
                orderManagement1.setStatus(orderManagement.getStatus());
                orderManagement1.setNote(noteOrderManagementVo.getNote());
                orderManagementDAO.save(orderManagement1);
                return true;
            }
        }
    }

    @Override
    public ShowProductWarrantyVO getWarranty(Integer orderId, Principal principal) {
        checkPrincipal(principal);
        ShowProductWarrantyVO showProductWarrantyVO = new ShowProductWarrantyVO();
        Orders orders1 = ordersDAO.findMotById(orderId);
        if (orders1 == null) {
            throw new NotFoundException("api.error.API-003");
        }
        showProductWarrantyVO.setStatus(getStatus(orderId));
        if (showProductWarrantyVO.getStatus().equals("Giao hàng thành công")) {
            List<OrderDetails> orderDetails = orderDetailsDAO.findAllByOrderIdEquals(orderId);
            List<ProductVO> productVOS = new ArrayList<>();
            for (OrderDetails orderDetail : orderDetails
            ) {
                Product product = orderDetail.getProduct();
                if (warrantyDAO.findOneByOrderIdAndProductId(orderId, product.getId()) != null) {
                    continue;
                } else {
                    ProductVO productVO = new ProductVO();
                    BeanUtils.copyProperties(product, productVO);
                    productVOS.add(productVO);
                }
            }

            BeanUtils.copyProperties(orders1, showProductWarrantyVO);
            showProductWarrantyVO.setProductVOS(productVOS);

        } else {
            throw new NotImplementedException("Không có hóa đơn này " + orderId);
        }
        return showProductWarrantyVO;
    }

    @Override
    public OrdersVO newOrderAdmin(NewOrdersVO ordersVO, Principal principal) {
        checkPrincipal(principal);
        String changeBy = principal != null ? principal.getName() : "guest";
        Orders orders = createOders(ordersVO, changeBy);
        OrdersVO vo = new OrdersVO();
        vo = ordersVO;
//        BeanUtils.copyProperties(ordersVO,vo);
        saveDetails(orders, vo);
        return managerOrderStatus(orders, changeBy, "Đã xác nhận");
    }

    @Override
    public OrdersVO updateOrderAdmin(Optional<Integer> id, Optional<String> status, Principal principal) {
        checkPrincipal(principal);
        Orders orders = ordersDAO.getById(id.get());
        return managerOrderStatus(orders, principal.getName(), status.get());
    }

    @Override
    public List<OrdersVO> getList(Principal principal, Optional<Integer> id, Optional<String> email, Optional<String> name, Optional<String> phone) {
        checkPrincipal(principal);
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
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        List<OrdersVO> ordersVOS = new ArrayList<>();
        for (Orders orders : ordersDAO.getByUsername(principal.getName())) {
            OrdersVO vo = new OrdersVO();
            BeanUtils.copyProperties(orders, vo);
            vo.setStatus(getStatus(orders.getId()));
            vo.setNumOfProduct(orderDetailsDAO.getCountProductOf(vo.getId()));
            ordersVOS.add(vo);
        }
        Collections.sort(ordersVOS, Comparator.comparing(OrdersVO::getDateCreated).reversed());
        return ordersVOS;
    }

    @Override
    public List<OrdersVO> getAll(Principal principal) {
        checkPrincipal(principal);
        List<Orders> orders = ordersDAO.findAll();
        List<OrdersVO> ordersVOS = new ArrayList<>();
        for (Orders order : orders) {
            OrdersVO vo = new OrdersVO();
            BeanUtils.copyProperties(order, vo);

            //listcustomer
            Customer customer = customerDAO.findCustomerById(order.getCustomerId());
            if (customer == null) {
                throw new NotFoundException("api.error.API-003");
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
            vo.setNumOfProduct(orderDetailsDAO.getCountProductOf(vo.getId()));
            ordersVOS.add(vo);
        }
        Collections.sort(ordersVOS, Comparator.comparing(OrdersVO::getDateCreated).reversed());
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

    private Orders createOders(@Valid NewOrdersVO ordersVO, String changeBy) {

        List<OrderDetailsVO> orderDetailsVO = ordersVO.getOrderDetails();
        Long totalPrice = 0L;
        Long totalDiscount = 0L;
        for (OrderDetailsVO orderDetailsVO1 : orderDetailsVO) {
            if (orderDetailsVO1.getQuantity() <= 0) {
                continue;
            } else {
                Long discount = orderDetailsVO1.getQuantity() * orderDetailsVO1.getDiscount();
                totalDiscount += discount;
                totalPrice += orderDetailsVO1.getQuantity() * (orderDetailsVO1.getPrice() - orderDetailsVO1.getDiscount());
            }
        }
        if (!totalPrice.equals(ordersVO.getSumprice())) {
            throw new NotImplementedException("Giá sản phẩm đã thay đổi. xin mời xem lại chương trình khuyến mại");
        }

        Customer customer = createCustomer(ordersVO.getCustomer());
//        Customer customer = new Customer();
//        BeanUtils.copyProperties(ordersVO.getCustomer(), customer);
//        customer = customerDAO.save(customer);
        //save order
        Orders orders = new Orders();
        orders.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        orders.setUsername(changeBy);
        orders.setCustomerId(customer.getId());
        orders.setTypePayment(false);
        orders.setSumprice(totalPrice);
        orders = ordersDAO.save(orders);

        return orders;
    }

    private Customer createCustomer(@Valid CustomerVO customerVO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerVO, customer);
        return customerDAO.save(customer);
    }

    private List<OrderDetailsVO> saveDetails(Orders orders, OrdersVO ordersVO) {
        // save order detail
        List<OrderDetailsVO> vos = new ArrayList<>();
        List<OrderDetailsVO> orderDetailsVOS = ordersVO.getOrderDetails();
        for (OrderDetailsVO orderDetailsVO1 : orderDetailsVOS) {
            if (orderDetailsVO1.getQuantity().equals(0)) {
                continue;
            }
            OrderDetails orderDetails = new OrderDetails();
            BeanUtils.copyProperties(orderDetailsVO1, orderDetails);
            orderDetails.setOrderId(orders.getId());
            orderDetails.setDiscount(priceUtils.maxDiscountAtPresentOf(orderDetails.getProductId()));
            ProductColor productColor = productColorDAO.findByProductIdAndColorId(orderDetails.getProductId(), orderDetails.getColorId());
            if (productColor == null || productColor.getQuantity() - orderDetails.getQuantity() < 0) {
                throw new NotImplementedException("Sản phẩm không có màu "+orderDetails.getColorId() + "hoặc số lượng sản phẩm màu này không đủ");
            }
            productColor.setQuantity(productColor.getQuantity() - orderDetails.getQuantity());
            productColorDAO.save(productColor);
            orderDetails.setStatusWarranty(false);
            orderDetails.setStatusWarranty(false);
            orderDetails = orderDetailsDAO.save(orderDetails);
            ProductSale productSale = priceUtils.getSaleHavingMaxDiscountOf(orderDetails.getProductId());
            if (productSale == null)
                continue;
            productSale.setQuantity(productSale.getQuantity() - orderDetails.getQuantity());
            productSaleDAO.save(productSale);
            OrderDetailsVO vo = new OrderDetailsVO();
            BeanUtils.copyProperties(orderDetails, vo);
            vos.add(vo);
        }
        return vos;
    }

    private OrdersVO managerOrderStatus(Orders orders, String changeBy, String status) {
        //save ordermanagement

        String note = "";
        if (status.equals("Đã xác nhận")) {
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
        if (orderManagement == null) {
            OrderManagement orderManagement1 = new OrderManagement();
            orderManagement1.setOrderId(id);
            orderManagement1.setStatus("Đơn hàng lỗi");
            orderManagement1.setChangedBy("SYSTEM");
            orderManagement1.setNote("Đơn hàng lỗi, hệ thống tự thêm trạng thái");
            orderManagement1.setTimeChange(Timestamp.valueOf(LocalDateTime.now()));
            orderManagement1 = orderManagementDAO.save(orderManagement1);
            return orderManagement1.getStatus();
        }
        return orderManagement.getStatus();
    }

    private OrdersVO getStatusLine(OrdersVO ordersVO) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        OrdersVO vo = ordersVO;
        List<OrderManagement> oms = orderManagementDAO.findByOrderId(vo.getId());
        Collections.sort(oms, Comparator.comparing(OrderManagement::getTimeChange).reversed());
        List<OrderManagementVO> omvos = new ArrayList<>();
        for (OrderManagement orderManagement : oms) {
            OrderManagementVO managementVO = new OrderManagementVO();
            BeanUtils.copyProperties(orderManagement, managementVO);
            managementVO.setTimeChange(sdf.format(new Date(orderManagement.getTimeChange().getTime())));
            omvos.add(managementVO);
        }
        vo.setOrderManagements(omvos);
        return vo;
    }

}
