package com.poly.datn.service;

import com.poly.datn.entity.Warranty;
import com.poly.datn.vo.WarrantyVO;
import javassist.NotFoundException;
import org.springframework.beans.BeansException;

import java.security.Principal;
import java.util.List;

public interface WarrantyService {
    List<WarrantyVO> getAll(Principal principal) throws BeansException;
    List<WarrantyVO> getByUsername(Principal principal) throws NotFoundException;
    WarrantyVO newWarranty(WarrantyVO warrantyVO, Principal principal) throws NotFoundException;
}
