package com.poly.datn.service;

import com.poly.datn.vo.ProductSaleVO;
import com.poly.datn.vo.SaleVO;

import java.security.Principal;
import java.util.List;

public interface SaleService {
    List<SaleVO> getAll(Principal principal);

    SaleVO getById(Principal principal, Integer id);

    List<SaleVO> getSaleNow(Principal principal);

    List<SaleVO> getSaleAboutStart(Principal principal);

    List<SaleVO> getSellEnd(Principal principal);

    Long getCurrentSaleOf(Integer productId);

    SaleVO newSale(SaleVO saleVO, Principal principal);

    SaleVO updateSale(SaleVO saleVO, Principal principal);

    boolean stopSale(Integer id, Principal principal);

    ProductSaleVO newProductSale(ProductSaleVO productSaleVO, Principal principal);

    ProductSaleVO updateProductSale(ProductSaleVO productSaleVO, Principal principal);

    boolean deleteProductSale(Integer id, Principal principal);
}
