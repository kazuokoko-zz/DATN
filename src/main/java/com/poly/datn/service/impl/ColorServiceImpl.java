package com.poly.datn.service.impl;

import com.poly.datn.common.Constant;
import com.poly.datn.dao.ColorDAO;
import com.poly.datn.dao.ProductColorDAO;
import com.poly.datn.entity.Color;
import com.poly.datn.entity.ProductColor;
import com.poly.datn.service.ColorService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ColorVO;
import com.poly.datn.vo.ProductColorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ColorServiceImpl implements ColorService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CheckRole checkRole;

    @Autowired
    ColorDAO colorDAO;

    @Autowired
    ProductColorDAO productColorDAO;

    @Override
    public List<ColorVO> getColorProduct(Principal principal) {
        if(principal == null){
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        } else if (checkRole.isHavePermition(principal.getName(), "Director")) {
            List<ColorVO> colorVO = new ArrayList<>();
        colorDAO.findAll().forEach(color -> {
            ColorVO vo = new ColorVO();
            BeanUtils.copyProperties(color, vo);
            colorVO.add(vo);
        });
            return colorVO;

    } else {
        return null;
    }
    }

    @Override
    public ColorVO addColorProduct(ColorVO colorVO, Principal principal) {
        if(principal == null){
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        } else if (checkRole.isHavePermition(principal.getName(), "Director")) {
            Color color = new Color();
            if (colorDAO.findByColorName(colorVO.getColorName()) == null) {
                BeanUtils.copyProperties(colorVO, color);
                color = colorDAO.save(color);
                colorVO.setId(color.getId());
                return colorVO;
            }
            {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public ColorVO updateColorProduct(ColorVO colorVO, Principal principal) {
        if(principal == null){
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        } else if (checkRole.isHavePermition(principal.getName(), "Director")) {
            Optional<Color> optionalColor = colorDAO.findById(colorVO.getId());
        if(optionalColor.isPresent()){
            Color entityColor = new Color();
            BeanUtils.copyProperties(colorVO, entityColor);
            colorDAO.save(entityColor);
        }
        return  colorVO;
        } else {
            return null;
        }
    }

    @Override
    public Color deleteColorProduct(Integer id, Principal principal) {
        if(principal == null){
            log.error(Constant.NOT_LOGGED_IN);
            return null;
        } else if (checkRole.isHavePermition(principal.getName(), "Director")) {
            Color color= colorDAO.findColorById(id);
            if(color != null) {
                colorDAO.delete(color);
                ProductColor productColor = productColorDAO.findByColorId(color.getId());
                if (productColor != null) {
                    productColorDAO.delete(productColor);
                } else {
                    return null;
                }
                return color;
            } else {
                return null;
            }
    } else {
        return null;
    }
    }
}
