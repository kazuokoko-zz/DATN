package com.poly.datn.service.impl;


import com.poly.datn.common.Constant;
import com.poly.datn.dao.ProductSaleDAO;
import com.poly.datn.dao.SaleDAO;
import com.poly.datn.entity.ProductSale;
import com.poly.datn.entity.Sale;
import com.poly.datn.service.SaleService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.ProductSaleVO;
import com.poly.datn.vo.SaleVO;
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
                throw new NotFoundException("api.error.API-003");
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
                throw new NotFoundException("api.error.API-003");
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
                throw new NotFoundException("api.error.API-003");
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
                throw new NotFoundException("api.error.API-003");
            }
        } else {
            return null;
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
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            throw new NotFoundException("api.error.API-003");
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                Sale sale = new Sale();
                BeanUtils.copyProperties(saleVO, sale);
                if (LocalDateTime.now().isBefore(sale.getStartTime().toLocalDateTime())) {
                    sale.setStatus("Săp diễn ra");
                } else if (LocalDateTime.now().isAfter(sale.getEndTime().toLocalDateTime())) {
                    sale.setStatus("Đã kết thúc");
                }else{
                    sale.setStatus("Đang diễn ra");
                }
                sale = saleDAO.save(sale);
                saleVO.setId(sale.getId());
                return saleVO;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new NotFoundException("api.error.API-003");
        }
    }

    @Override
    public SaleVO updateSale(SaleVO saleVO, Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            throw new NotFoundException("api.error.API-003");
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                Sale sale = saleDAO.getById(saleVO.getId());
                if (sale.getStatus().equals("Đã kết thúc") || sale.getStatus().equals("Đã dừng")) {
                    throw new NotFoundException("api.error.API-003");
                }
                saleVO.setId(sale.getId());
                Long timestart = sale.getStartTime().getTime();
                Long localDateTime1 = Timestamp.valueOf(LocalDateTime.now()).getTime();

                if (timestart - localDateTime1 <= 0) {
                    saleVO.setName(sale.getName());
                    saleVO.setStartTime(sale.getStartTime());
                    saleVO.setName(sale.getStatus());
                } else {
                    saleVO.setName(saleVO.getName());
                    saleVO.setStartTime(saleVO.getStartTime());
                    saleVO.setName(sale.getStatus());
                }
                BeanUtils.copyProperties(saleVO, sale);
                sale = saleDAO.save(sale);
                saleVO.setId(sale.getId());
                return saleVO;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new NotFoundException("api.error.API-003");
        }
    }

    @Override
    public boolean stopSale(Integer id, Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            throw new NotFoundException("api.error.API-003");
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                Sale sale = saleDAO.getById(id);
                sale.setStatus("Đã dừng");
                saleDAO.save(sale);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new NotFoundException("api.error.API-003");
        }
    }

    @Override
    public ProductSaleVO newProductSale(ProductSaleVO productSaleVO, Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            throw new NotFoundException("api.error.API-003");
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                ProductSale productSale = new ProductSale();
                BeanUtils.copyProperties(productSaleVO, productSale);
                productSale = productSaleDAO.save(productSale);
                productSaleVO.setId(productSale.getId());
                return productSaleVO;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new NotFoundException("api.error.API-003");
        }
    }

    @Override
    public ProductSaleVO updateProductSale(ProductSaleVO productSaleVO, Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            throw new NotFoundException("api.error.API-003");
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                ProductSale productSale = productSaleDAO.findOneById(productSaleVO.getId());
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
        } else {
            throw new NotFoundException("api.error.API-003");
        }
    }

    @Override
    public boolean deleteProductSale(Integer id, Principal principal) {
        if (principal == null) {
            log.error(Constant.NOT_LOGGED_IN);
            throw new NotFoundException("api.error.API-003");
        } else if (checkRole.isHavePermition(principal.getName(), "Director")
                || checkRole.isHavePermition(principal.getName(), "Staff")) {
            try {
                ProductSale productSale = productSaleDAO.findOneById(id);
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
        } else {
            throw new NotFoundException("api.error.API-003");
        }
    }

}
