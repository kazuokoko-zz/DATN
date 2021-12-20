package com.poly.datn.service.impl;

import com.poly.datn.dao.ProductColorDAO;
import com.poly.datn.dao.ProductDAO;
import com.poly.datn.entity.Product;
import com.poly.datn.entity.ProductColor;
import com.poly.datn.service.ProductColorService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ProductColorVO;
import com.poly.datn.vo.VoBoSung.ProductDTO.UpdateProductColorDTO;
import org.apache.commons.lang.NotImplementedException;
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
public class ProductColorServiceImpl implements ProductColorService {

    @Autowired
    ProductColorDAO productColorDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CheckRole checkRole;

    private Integer productId;

    @Override
    public List<ProductColorVO> newProductColor(Optional<Integer> id, List<ProductColorVO> productColorVOS, Optional<String> statusProduct, Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        if (!id.isPresent() || !(checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
        }
        if (productColorVOS == null) {
            throw new NotImplementedException("Chưa thêm chi tiết sản phẩm");
        }
        productColorDAO.deleteAllByProductIdEquals(id.get());
        for (ProductColorVO productColorVO : productColorVOS) {
            ProductColor productColor = new ProductColor();
            BeanUtils.copyProperties(productColorVO, productColor);
            productColor.setProductId(id.get());
            productColor = productColorDAO.save(productColor);
            productId = productColor.getProductId();
        }
        Product product = productDAO.getOneProductById(productId);
        if (statusProduct.isPresent()) {
            if (statusProduct.get().equals("Không kinh doanh") || statusProduct.get().equals("Đang bán")) {
                product.setStatus(statusProduct.get());
            } else {
                product.setStatus("đã hoàn thành nhập thông tin");
            }
        } else {
            product.setStatus("đã hoàn thành nhập thông tin");
        }
        productDAO.save(product);
        productColorVOS = new ArrayList<>();
        for (ProductColor productColor : productColorDAO.findAllByProductIdEquals(id.get())) {
            ProductColorVO productColorVO = new ProductColorVO();
            BeanUtils.copyProperties(productColor, productColorVO);
            productColorVOS.add(productColorVO);
        }
        return productColorVOS;
    }
    public void updateProductColor(Integer id, List<UpdateProductColorDTO> productColorVOS) {
        productColorDAO.deleteAllByProductIdEquals(id);
        for (UpdateProductColorDTO productColorVO : productColorVOS) {
            ProductColor productColor = new ProductColor();
            BeanUtils.copyProperties(productColorVO, productColor);
            productColor.setProductId(id);
            productColor = productColorDAO.save(productColor);
            productId = productColor.getProductId();
        }
        productColorVOS = new ArrayList<>();
        for (ProductColor productColor : productColorDAO.findAllByProductIdEquals(id)) {
            UpdateProductColorDTO productColorVO = new UpdateProductColorDTO();
            BeanUtils.copyProperties(productColor, productColorVO);
            productColorVOS.add(productColorVO);
        }
    }
}
