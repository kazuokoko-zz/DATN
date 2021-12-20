package com.poly.datn.service.impl;

import com.poly.datn.dao.*;
import com.poly.datn.entity.*;
import com.poly.datn.service.WarrantyService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.WarrantyInvoiceVO;
import com.poly.datn.vo.WarrantyVO;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class WarrantyServiceImpl implements WarrantyService {

    @Autowired
    WarrantyDAO warrantyDAO;

    @Autowired
    OrdersDAO ordersDAO;
    @Autowired
    OrderDetailsDAO orderDetailsDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    OrderManagementDAO orderManagementDAO;
    @Autowired
    CheckRole checkRole;
    @Autowired
    WarrantyInvoiceDAO warrantyInvoiceDAO;
    @Autowired
    ProductDAO productDAO;


    public void checkPrincipal(Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
        }
    }

    @Override
    public List<WarrantyVO> getAll(Principal principal) {
        checkPrincipal(principal);
        List<Warranty> warranties = warrantyDAO.findAll();
        List<WarrantyVO> warrantyVOS = new ArrayList<>();
        for (Warranty warranty : warranties) {
            WarrantyVO warrantyVO = new WarrantyVO();
            BeanUtils.copyProperties(warranty, warrantyVO);
            warrantyVOS.add(warrantyVO);
        }
        return warrantyVOS;
    }

    @Override
    public WarrantyVO getAllById(Integer id, Principal principal) {
        checkPrincipal(principal);
        try {
            Warranty warranties = warrantyDAO.getById(id);
            if (warranties == null) {
                throw new NotImplementedException("Không tồn tại hóa đơn bảo hành này");
            }
            WarrantyVO warrantyVO = new WarrantyVO();
            BeanUtils.copyProperties(warranties, warrantyVO);
            List<WarrantyInvoice> warrantyInvoice = warrantyInvoiceDAO.findByWarrantyId(id);
            List<WarrantyInvoiceVO> warrantyInvoiceVO = new ArrayList<>();
            for (WarrantyInvoice warrantyInvoice1 : warrantyInvoice
            ) {
                WarrantyInvoiceVO warrantyInvoiceVO1 = new WarrantyInvoiceVO();
                BeanUtils.copyProperties(warrantyInvoice1, warrantyInvoiceVO1);
                warrantyInvoiceVO.add(warrantyInvoiceVO1);
            }
            warrantyVO.setWarrantyInvoiceVOS(warrantyInvoiceVO);
            return warrantyVO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<WarrantyVO> getByUsername(Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        try {
            List<Orders> orders = ordersDAO.findByUsername(principal.getName());
            if (orders.size() == 0) {
                throw new NotFoundException("common.error.not-found");
            }
            List<WarrantyVO> warrantyVOS = new ArrayList<>();
            for (Orders orders1 : orders) {
                Warranty warranties = warrantyDAO.findOneByOrderId(orders1.getId());
                if (warranties == null) {
                    throw new NotFoundException("common.error.not-found");
                }
                WarrantyVO warrantyVO = new WarrantyVO();
                BeanUtils.copyProperties(warranties, warrantyVO);
                warrantyVOS.add(warrantyVO);
            }
            return warrantyVOS;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public WarrantyVO newWarranty(@Valid WarrantyVO warrantyVO, Principal principal) {
        checkPrincipal(principal);
        try {
            Warranty warranty = new Warranty();
            Orders orders1 = ordersDAO.findMotById(warrantyVO.getOrderId());
            if (orders1 == null) {
                throw new NotImplementedException("Không có hóa đơn này");
            }
            Orders orders = ordersDAO.findOneByIdForWarranty(orders1.getId(), warrantyVO.getProductId());
            if (orders != null) {
                throw new NotImplementedException("Đã có hóa đơn cho sản phẩm này");
            }
            OrderDetails orderDetails = orderDetailsDAO.findOneByOrderIdAndProductIdAndColorId(orders1.getId(), warrantyVO.getProductId(), warrantyVO.getColorId());
            BeanUtils.copyProperties(warrantyVO, warranty);
            warranty.setProductId(orderDetails.getProductId());
            warranty.setOrderId(orderDetails.getOrderId());
            warranty.setName(customerDAO.findCustomerById(orders1.getCustomerId()).getFullname());
            warranty.setPhone(customerDAO.findCustomerById(orders1.getCustomerId()).getPhone());
            warranty.setAddress(customerDAO.findCustomerById(orders1.getCustomerId()).getAddress());
            warranty.setCreateDate(Date.valueOf(LocalDate.now()));
            warranty.setStatus(1);
            warranty.setCountWarranty(0);
            warranty.setCreateBy(principal.getName());
            warranty.setExpiredDate(Date.from(LocalDate.now().plusMonths(productDAO.getById(warrantyVO.getProductId()).getWarranty())
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

            warranty = warrantyDAO.save(warranty);
            warrantyVO.setId(warranty.getId());
            warrantyVO.setExpiredDate(warranty.getExpiredDate());
            warrantyVO.setStatus(warranty.getStatus());
            OrderManagement orderManagements = orderManagementDAO.getLastManager(warrantyVO.getOrderId());
            ;
            OrderManagement orderManagement = new OrderManagement();
            orderManagement.setChangedBy(principal.getName());
            orderManagement.setStatus(orderManagements.getStatus());
            orderManagement.setNote("Đã tạo hóa đơn bảo hành cho sản phẩm có seri: " + warrantyVO.getProductSeri());
            Long datetime = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(datetime);
            orderManagement.setTimeChange(timestamp);
            orderManagement.setOrderId(warrantyVO.getOrderId());
            orderManagementDAO.save(orderManagement);
            orderDetails.setStatusWarranty(true);
            orderDetailsDAO.save(orderDetails);
            return warrantyVO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
