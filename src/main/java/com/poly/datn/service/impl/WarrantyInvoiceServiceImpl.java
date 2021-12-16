package com.poly.datn.service.impl;


import com.poly.datn.dao.WarrantyDAO;
import com.poly.datn.dao.WarrantyInvoiceDAO;
import com.poly.datn.entity.Warranty;
import com.poly.datn.entity.WarrantyInvoice;
import com.poly.datn.service.WarrantyInvoiceService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.WarrantyInvoiceVO;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WarrantyInvoiceServiceImpl implements WarrantyInvoiceService {
    @Autowired
    CheckRole checkRole;

    @Autowired
    WarrantyInvoiceDAO warrantyInvoiceDAO;

    @Autowired
    WarrantyDAO warrantyDAO;


    @Override
    public Object create(WarrantyInvoiceVO invoiceVO, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        WarrantyInvoice invoice = new WarrantyInvoice();
        BeanUtils.copyProperties(invoiceVO, invoice);
        Warranty warranty = warrantyDAO.getById(invoiceVO.getInvoice());
        if (warranty == null) {
            throw new NotImplementedException("Sai mã phiều bảo hành");
        } else {
            if (warranty.getStatus() == 1) {
                warranty.setStatus(0);
                warrantyDAO.save(warranty);
            }
            invoice = warrantyInvoiceDAO.save(invoice);
        }
        BeanUtils.copyProperties(invoice, invoiceVO);
        return invoiceVO;
    }

    @Override
    public Object getById(Optional<Integer> id, Principal principal) {

        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) || !id.isPresent()) {
            return null;
        }
        WarrantyInvoice invoice = warrantyInvoiceDAO.getById(id.get());
        if (invoice == null) {
            throw new NotImplementedException("Không tìm thấy");
        }
        WarrantyInvoiceVO invoiceVO = new WarrantyInvoiceVO();
        BeanUtils.copyProperties(invoice, invoiceVO);

        return invoiceVO;
    }

    @Override
    public List<WarrantyInvoiceVO> getAll(Principal principal) {
        return null;
    }
}
