package com.poly.datn.service.impl;


import com.poly.datn.dao.ProductDAO;
import com.poly.datn.dao.ProductSaleDAO;
import com.poly.datn.dao.SaleDAO;
import com.poly.datn.entity.ProductSale;
import com.poly.datn.entity.Sale;
import com.poly.datn.service.SaleService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ProductSaleVO;
import com.poly.datn.vo.SaleVO;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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

    @Autowired
    ProductDAO productDAO;

    public void checkPrincipal(Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
        }
    }

    @Override
    public List<SaleVO> getAll(Principal principal) {
        checkPrincipal(principal);
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
            throw new NotFoundException("api.error.API-003");
        }
    }


    @Override
    public SaleVO getById(Principal principal, Integer id) {
        checkPrincipal(principal);
        Sale sale = saleDAO.getById(id);
        SaleVO saleVO = new SaleVO();
        BeanUtils.copyProperties(sale, saleVO);
        List<ProductSaleVO> productSaleVOS = new ArrayList<>();
        for (ProductSale productSale : productSaleDAO.getAllBySaleIdEquals(id)) {
            ProductSaleVO productSaleVO = new ProductSaleVO();
            BeanUtils.copyProperties(productSale, productSaleVO);
            productSaleVOS.add(productSaleVO);
        }
        saleVO.setProductSaleVO(productSaleVOS);
        return saleVO;
    }

    @Override
    public List<SaleVO> getSaleNow(Principal principal) {
        checkPrincipal(principal);
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
            throw new NotFoundException("api.error.API-003");
        }
    }

    @Override
    public List<SaleVO> getSaleAboutStart(Principal principal) {
        checkPrincipal(principal);
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
            throw new NotFoundException("api.error.API-003");
        }
    }

    @Override
    public List<SaleVO> getSellEnd(Principal principal) {
        checkPrincipal(principal);
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
            throw new NotFoundException("api.error.API-003");
        }
    }

    @Override
    public Long getCurrentSaleOf(Integer productId) {
        List<Sale> sales = saleDAO.findSalesAt(Timestamp.valueOf(LocalDateTime.now()));
        for (Sale sale : sales) {
            Optional<ProductSale> productSale = productSaleDAO.findByProductIdEqualsAndSaleIdEquals(productId, sale.getId());
            if (productSale.isPresent())
                return productSale.get().getDiscount();
        }
        return 0L;
    }

    @Override
    public SaleVO newSale(SaleVO saleVO, Principal principal) {
        checkPrincipal(principal);
        try {
            Sale sale = new Sale();
            BeanUtils.copyProperties(saleVO, sale);
            if (LocalDateTime.now().isBefore(sale.getStartTime().toLocalDateTime())
                    && (sale.getEndTime().toLocalDateTime()).isAfter(sale.getStartTime().toLocalDateTime())) {
                sale.setStatus("Săp diễn ra");
            } else {
                throw new NotImplementedException("Không  được tạo chương trình với thời gian này");
            }
            sale = saleDAO.save(sale);
            PropertyUtils.copyProperties(saleVO, sale);
            return saleVO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SaleVO updateSale(SaleVO saleVO, Principal principal) {
        checkPrincipal(principal);
        try {
            Sale sale = saleDAO.getById(saleVO.getId());
            if (sale.getStatus().equals("Đã kết thúc")) {
                throw new NotImplementedException("Không thể thay đổi chương trình sale đã kết thúc");
            }
            saleVO.setId(sale.getId());
            Long timestart = sale.getStartTime().getTime();
            Long localDateTime1 = Timestamp.valueOf(LocalDateTime.now()).getTime();
            if (timestart - localDateTime1 <= 0) {
                if (!saleVO.getStartTime().equals(sale.getStartTime())) {
                    throw new NotImplementedException("Không  được sửa thời gian chương trình đã diễn ra");
                }
                if ((sale.getEndTime().toLocalDateTime()).isBefore(LocalDateTime.now())) {
                    throw new NotImplementedException("Không  được sửa thời gian chương trình đã kết thúc");
                }
                saleVO.setName(sale.getName());
                saleVO.setStartTime(sale.getStartTime());
                saleVO.setName(sale.getStatus());
            } else {
                saleVO.setName(saleVO.getName());
                saleVO.setStartTime(saleVO.getStartTime());
                saleVO.setName(sale.getStatus());
            }
            saleVO.setStatus(sale.getStatus());
            BeanUtils.copyProperties(saleVO, sale);

            sale = saleDAO.save(sale);
            PropertyUtils.copyProperties(saleVO, sale);
            return saleVO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean stopSale(Integer id, Principal principal) {
        checkPrincipal(principal);
        try {
            Sale sale = saleDAO.getById(id);
            sale.setStatus("Đã dừng");
            saleDAO.save(sale);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean continueSale(Integer id, Principal principal) {
        checkPrincipal(principal);
        try {
            Sale sale = saleDAO.getById(id);
            if(LocalDateTime.now().isAfter(sale.getEndTime().toLocalDateTime())){
                throw new NotImplementedException("Chương trình sale đã kết thúc");
            }
            if(!sale.getStatus().equals("Đã dừng")){
                throw new NotImplementedException("Không thể tiếp tục chương trình đang không tạm dừng");
            }
            sale.setStatus("Đang diễn ra");
            saleDAO.save(sale);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductSaleVO newProductSale(ProductSaleVO productSaleVO, Principal principal) {
        checkPrincipal(principal);
        try {
            if (productDAO.getOneProductById(productSaleVO.getProductId()) == null) {
                throw new NotImplementedException("Không tồn tại sản phẩm này");
            }
            Sale sale = saleDAO.getOneById(productSaleVO.getSaleId());
            if (sale == null || sale.getStatus().equals("Đã kết thúc")) {
                throw new NotImplementedException("Không thể thêm sản phẩm vào chương trình này");
            }
            if (productSaleDAO.findByProductIdAndSaleId(productSaleVO.getProductId(), productSaleVO.getSaleId()) != null) {
                throw new NotImplementedException("Sản phẩm này đang được sale trong chương trình này rồi");
            }
            ProductSale productSale = new ProductSale();
            BeanUtils.copyProperties(productSaleVO, productSale);
            productSale = productSaleDAO.save(productSale);
            productSaleVO.setId(productSale.getId());
            return productSaleVO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductSaleVO updateProductSale(ProductSaleVO productSaleVO, Principal principal) {
        checkPrincipal(principal);
        try {
            ProductSale productSale = productSaleDAO.findByProductIdAndSaleId(productSaleVO.getProductId(), productSaleVO.getSaleId());
            if (productSale == null) {
                throw new NotFoundException("api.error.API-003");
            }
            Sale sale = saleDAO.getById(productSaleVO.getSaleId());
            if (sale.getStatus().equals("Đã kết thúc") || sale.getStatus().equals("Đã dừng")) {
                throw new NotFoundException("api.error.API-003");
            }
            productSaleVO.setId(productSale.getId());
            productSaleVO.setProductId(productSale.getProductId());
            productSaleVO.setSaleId(productSale.getSaleId());

            Long timestart = sale.getStartTime().getTime();
            Long localDateTime1 = Timestamp.valueOf(LocalDateTime.now()).getTime();
            if (timestart - localDateTime1 <= 0) {
                productSaleVO.setDiscount(productSale.getDiscount());
            }
            BeanUtils.copyProperties(productSaleVO, productSale);
            productSale = productSaleDAO.save(productSale);
            productSaleVO.setId(productSale.getId());


            return productSaleVO;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteProductSale(ProductSaleVO productSaleVO, Principal principal) {
        checkPrincipal(principal);
        try {
            ProductSale productSale = productSaleDAO.findByProductIdAndSaleId(productSaleVO.getProductId(), productSaleVO.getSaleId());
            if (productSale == null) {
                throw new NotFoundException("api.error.API-003");
            }
            Sale sale = saleDAO.getById(productSale.getSaleId());
            if (sale.getStatus().equals("Đã kết thúc") || sale.getStatus().equals("Đã dừng")) {
                throw new NotFoundException("api.error.API-003");
            }
            productSale.setQuantity(0);
            productSaleDAO.save(productSale);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
