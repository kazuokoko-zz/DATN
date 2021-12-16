package com.poly.datn.service;

import com.poly.datn.vo.WarrantyInvoiceVO;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface WarrantyInvoiceService {
    Object create(WarrantyInvoiceVO invoiceVO, Principal principal);

    Object getById(Optional<Integer> id, Principal principal);

    List<WarrantyInvoiceVO> getAll(Principal principal);
}
