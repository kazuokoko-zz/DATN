package com.poly.datn.service;

import com.poly.datn.entity.Warranty;
import com.poly.datn.vo.WarrantyVO;
import javassist.NotFoundException;

import java.security.Principal;
import java.util.List;

public interface WarrantyService {
    List<WarrantyVO> getAll(Principal principal);
    List<WarrantyVO> getByUsername(Principal principal);
   WarrantyVO getAllById(Integer id, Principal principal);
    WarrantyVO newWarranty(WarrantyVO warrantyVO, Principal principal);
}
