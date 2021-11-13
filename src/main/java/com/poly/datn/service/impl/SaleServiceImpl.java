package com.poly.datn.service.impl;


import com.poly.datn.common.Constant;
import com.poly.datn.dao.ProductSaleDAO;
import com.poly.datn.dao.SaleDAO;
import com.poly.datn.entity.ProductSale;
import com.poly.datn.entity.Sale;
import com.poly.datn.service.SaleService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.SaleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {


    private static final Logger log = LoggerFactory.getLogger(CartDetailServiceImpl.class);


    @Autowired
    SaleDAO saleDAO;

    @Autowired
    CheckRole checkRole;

    @Autowired
    ProductSaleDAO productSaleDAO;

    @Override
    public List<SaleVO> getAll(Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return new ArrayList<>();
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                List<Sale> saleList = saleDAO.findAll();
                List<SaleVO> saleVOList = new ArrayList<>();
                saleList.forEach(sale -> {
                    SaleVO saleVO = new SaleVO();
                    BeanUtils.copyProperties(sale, saleVO);
                    saleVOList.add(saleVO);
                });
                return saleVOList;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public List<SaleVO> getSaleNow(Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return new ArrayList<>();
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                List<Sale> saleList = saleDAO.findSalesAt(timestamp);
                List<SaleVO> saleVOList = new ArrayList<>();
                saleList.forEach(sale -> {
                    SaleVO saleVO = new SaleVO();
                    BeanUtils.copyProperties(sale, saleVO);
                    saleVOList.add(saleVO);
                });
                return saleVOList;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public List<SaleVO> getSaleAboutStart(Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return new ArrayList<>();
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                List<Sale> saleList = saleDAO.findSalesAboutStart(timestamp);
                List<SaleVO> saleVOList = new ArrayList<>();
                saleList.forEach(sale -> {
                    SaleVO saleVO = new SaleVO();
                    BeanUtils.copyProperties(sale, saleVO);
                    saleVOList.add(saleVO);
                });
                return saleVOList;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public List<SaleVO> getSellEnd(Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            return new ArrayList<>();
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                List<Sale> saleList = saleDAO.findSalesEnd(timestamp);
                List<SaleVO> saleVOList = new ArrayList<>();
                saleList.forEach(sale -> {
                    SaleVO saleVO = new SaleVO();
                    BeanUtils.copyProperties(sale, saleVO);
                    saleVOList.add(saleVO);
                });
                return saleVOList;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public Integer getCurrentSaleOf(Integer productId) {
        List<Sale> sales = saleDAO.findSalesAt(Timestamp.valueOf(LocalDateTime.now()));
        for (Sale sale : sales) {
            Optional<ProductSale> productSale = productSaleDAO.findByProductIdEqualsAndSaleIdEquals(productId, sale.getId());
            if (productSale.isPresent())
                return productSale.get().getDiscount();
        }
        return 0;
    }
}
