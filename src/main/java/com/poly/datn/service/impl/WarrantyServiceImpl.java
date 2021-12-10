package com.poly.datn.service.impl;

import com.poly.datn.dao.OrderManagementDAO;
import com.poly.datn.dao.OrdersDAO;
import com.poly.datn.dao.WarrantyDAO;
import com.poly.datn.entity.OrderManagement;
import com.poly.datn.entity.Orders;
import com.poly.datn.entity.Warranty;
import com.poly.datn.service.WarrantyService;
import com.poly.datn.utils.CheckRole;
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
    OrderManagementDAO orderManagementDAO;
    @Autowired
    CheckRole checkRole;
    @Override
    public List<WarrantyVO> getAll(Principal principal) {
        if (principal == null) {
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        List<Warranty>warranties = warrantyDAO.findAll();
        List<WarrantyVO>warrantyVOS = new ArrayList<>();
        for(Warranty warranty : warranties){
            WarrantyVO warrantyVO = new WarrantyVO();
            BeanUtils.copyProperties(warranty, warrantyVO);
            warrantyVOS.add(warrantyVO);
        }
        return warrantyVOS;
    }

    @Override
    public List<WarrantyVO> getByUsername(Principal principal){
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        try {
            List<Orders> orders = ordersDAO.findByUsername(principal.getName());
            if(orders.size() == 0){
                throw new NotFoundException("common.error.not-found");
            }
            List<WarrantyVO> warrantyVOS = new ArrayList<>();
            for (Orders orders1 : orders) {
                Warranty warranties = warrantyDAO.findOneByOrderId(orders1.getId());
                if(warranties == null){
                    throw new NotFoundException("common.error.not-found");
                }
                WarrantyVO warrantyVO = new WarrantyVO();
                BeanUtils.copyProperties(warranties, warrantyVO);
                warrantyVOS.add(warrantyVO);
            }
            return warrantyVOS;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public WarrantyVO newWarranty(@Valid WarrantyVO warrantyVO, Principal principal)  {
        if (principal == null) {
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        try {
            Warranty warranty = new Warranty();



            List<Orders> orders = ordersDAO.findOneById(warrantyVO.getOrderId());
            if(orders.size() == 0){
                throw new NotFoundException("common.error.not-found");
            }

            BeanUtils.copyProperties(warrantyVO, warranty);
            warranty.setExpiredDate(Date.valueOf(LocalDate.now()));
            warranty.setStatus(0);
            warranty = warrantyDAO.save(warranty);
            warrantyVO.setId(warranty.getId());
            warrantyVO.setExpiredDate(warranty.getExpiredDate());
            warrantyVO.setStatus(warranty.getStatus());


            OrderManagement orderManagement = new OrderManagement();
//        = orderManagementDAO.findOneByOrderId(warrantyVO.getOrderId());
            orderManagement.setChangedBy(principal.getName());
            orderManagement.setStatus("chờ giao hàng");

            Long datetime = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(datetime);
            orderManagement.setTimeChange(timestamp);
            orderManagement.setOrderId(warrantyVO.getOrderId());
            orderManagementDAO.save(orderManagement);

            return  warrantyVO;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
