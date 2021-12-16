package com.poly.datn.service;

import com.poly.datn.entity.Color;
import com.poly.datn.vo.ColorVO;

import java.security.Principal;
import java.util.List;

public interface ColorService {
    List<ColorVO> getColor();

//    List<ColorVO> getColor(Principal principal);
    ColorVO addColor(ColorVO colorVO, Principal principal);
    ColorVO updateColor(ColorVO colorVO, Principal principal);
    Color deleteColor(Integer id, Principal principal);
}
