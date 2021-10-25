package com.poly.datn.service;

import com.poly.datn.entity.Color;
import com.poly.datn.vo.ColorVO;

import java.security.Principal;
import java.util.List;

public interface ColorService {
    List<ColorVO> getColorProduct(Principal principal);
    ColorVO addColorProduct(ColorVO colorVO, Principal principal);
    ColorVO updateColorProduct(ColorVO colorVO, Principal principal);
    Color deleteColorProduct(Integer id, Principal principal);
}
