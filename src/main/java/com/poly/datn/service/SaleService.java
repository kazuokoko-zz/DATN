package com.poly.datn.service;

import com.poly.datn.vo.SaleVO;
import java.security.Principal;
import java.util.List;

public interface SaleService {
    List<SaleVO> getAll(Principal principal);
    List<SaleVO> getSaleNow(Principal principal);
    List<SaleVO>  getSaleAboutStart(Principal principal);
    List<SaleVO>  getSellEnd(Principal principal);
    Integer getCurrentSaleOf(Integer productId);

}
