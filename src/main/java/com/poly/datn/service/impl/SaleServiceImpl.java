package com.poly.datn.service.impl;

import com.poly.datn.dao.ProductSaleDAO;
import com.poly.datn.dao.SaleDAO;
import com.poly.datn.entity.ProductSale;
import com.poly.datn.entity.Sale;
import com.poly.datn.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {
    @Autowired
    SaleDAO saleDAO;

    @Autowired
    ProductSaleDAO productSaleDAO;

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
