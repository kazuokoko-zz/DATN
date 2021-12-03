package com.poly.datn.utils;

import com.poly.datn.dao.ProductSaleDAO;
import com.poly.datn.dao.SaleDAO;
import com.poly.datn.entity.ProductSale;
import com.poly.datn.entity.Sale;
import com.poly.datn.vo.SaleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class PriceUtils {
    @Autowired
    SaleDAO saleDAO;
    @Autowired
    ProductSaleDAO productSaleDAO;

    public List<SaleVO> getSaleNow() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        List<Sale> saleList = saleDAO.findSalesAt(timestamp);
        List<SaleVO> saleVOS = new ArrayList<>();
        saleList.forEach(sale -> {
            SaleVO saleVO = new SaleVO();
            BeanUtils.copyProperties(sale, saleVO);
            saleVOS.add(saleVO);
        });
        return saleVOS;
    }

    public ProductSale getSaleHavingMaxDiscountOf(Integer productId) {
        List<SaleVO> saleVOList = getSaleNow();
        List<ProductSale> prs = new ArrayList<>();
        for (SaleVO saleVO : saleVOList) {
            if (saleVO.getStatus().equals("Đã kết thúc"))
                continue;
            ProductSale productSale = productSaleDAO.findByProductIdAndSaleId(productId, saleVO.getId());
            if (productSale == null)
                continue;
            if (productSale.getQuantity() <= 0)
                continue;
            prs.add(productSale);
        }
        if (prs.isEmpty())
            return null;

        Collections.sort(prs, Comparator.comparingLong(ProductSale::getDiscount).reversed());
        return prs.get(0);
    }

    public Long maxDiscountAtPresentOf(Integer productId) {
        ProductSale productSale = getSaleHavingMaxDiscountOf(productId);
        return productSale != null ? productSale.getDiscount() : 0L;

        /**
         *
         */

//        Long bonusProduct = 0L;
//
//        saleVOList = getSaleNow();
//
//        for (SaleVO saleVO : saleVOList) {
//            if (saleVO.getStatus().equals("Đã kết thúc")) {
//                bonusProduct = 0L;
//            } else {
//                ProductSale productSale = productSaleDAO.findByProductIdAndSaleId(productId, saleVO.getId());
//                if (productSale == null) {
//                    bonusProduct = 0L;
//                } else {
//                    if (productSale.getQuantity() <= 0) {
//                        bonusProduct = 0L;
//                        saleVO.setStatus("Đã kết thúc");
//                        Sale sale = new Sale();
//                        BeanUtils.copyProperties(saleVO, sale);
//                        saleDAO.save(sale);
//                    } else {
////                        if(){
//                        bonusProduct = productSale.getDiscount().longValue();
//
//                        productSale.setQuantity(productSale.getQuantity() - 1);
//                        if (productSale.getQuantity() <= 0) {
//                            Sale sale = new Sale();
//                            saleVO.setStatus("Đã kết thúc");
//                            BeanUtils.copyProperties(saleVO, sale);
//                            saleDAO.save(sale);
//
//                        }
//                    }
////                    }
//                }
//            }
//        }
//        return bonusProduct;
    }
}
