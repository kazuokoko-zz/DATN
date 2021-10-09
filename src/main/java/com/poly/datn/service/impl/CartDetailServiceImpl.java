package com.poly.datn.service.impl;

import com.poly.datn.VO.CartDetailVO;
import com.poly.datn.VO.OrdersVO;
import com.poly.datn.VO.ProductVO;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.CartDetailDAO;
import com.poly.datn.dao.ProductDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.entity.CartDetail;
import com.poly.datn.entity.Product;
import com.poly.datn.service.CartDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    CartDetailDAO cartDao;
    @Autowired
    ProductDAO productDAO;

    @Autowired
    CartDetailDAO cartDetailDAO;

    @Autowired
    AccountDAO accountDAO;
//    @Override
//    public CartDetail getById(Integer id) {
//        CartDetail cartDetail = cartDao.findById(id).get();
//        cartDetail.getId();
//        return cartDetail;
//    }

    @Override
    public List<CartDetailVO> findCartByUsername(Principal principal) {
        if (principal == null) {
            System.out.println("bạn chưa đăng nhập");
            return null;
        }
        List<CartDetailVO> cartDetailVOS = new ArrayList<>();
        cartDao.getCartDetailsByUsername(principal.getName()).forEach(orders -> {
            CartDetailVO vo = new CartDetailVO();
            BeanUtils.copyProperties(orders, vo);
            cartDetailVOS.add(vo);
        });
        return cartDetailVOS;
//
//        List<CartDetailVO> voList = new ArrayList<CartDetailVO>();
//        List<CartDetail> optional = cartDao.getCartDetailsByUsername(username);
//        for (CartDetail entity : optional) {
////            cartDao.getCartDetailsByUsername(username).forEach(cartDetail -> {
//                CartDetailVO vo = new CartDetailVO();
//                BeanUtils.copyProperties(entity, vo);
//                voList.add(vo);
////            });
//        }
//        return voList;
    }


    @Override
    @Transactional
    public boolean deleteCartDetaiilById(Integer id, Principal principal) {
        if (principal == null) {
            System.out.println("bạn chưa đăng nhập");
            return false;
        }
        CartDetailVO cartDetailVO = new CartDetailVO();
        Optional<CartDetail> optionalCartDetail = cartDao.findById(id);
        if (optionalCartDetail.isPresent()) {
            CartDetail entity = optionalCartDetail.get();
            BeanUtils.copyProperties(entity, cartDetailVO);
            cartDao.delete(entity);
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public CartDetailVO save(CartDetailVO cartDetailVO, Principal principal) {
        if (principal == null) {
            System.out.println("bạn chưa đăng nhập");
            return null;
        }
        CartDetail cartDetail = new CartDetail();
        if (cartDetailVO.getId() == null) {
            BeanUtils.copyProperties(cartDetailVO, cartDetail);
            cartDetail.setUserId(accountDAO.findAccountByUsername(principal.getName()).getId());
            cartDetail = cartDetailDAO.save(cartDetail);

        } else if (cartDetailVO.getQuantity() <= 0) {
            cartDetailDAO.deleteById(cartDetailVO.getId());
            return null;
        } else {
            cartDetail = cartDetailDAO.getById(cartDetailVO.getId());
            cartDetail.setQuantity(cartDetailVO.getQuantity());
            cartDetailDAO.save(cartDetail);
        }
        BeanUtils.copyProperties(cartDetail, cartDetailVO);
        return cartDetailVO;

//        Optional<CartDetail> optionalCartDetail = cartDao.findById(cartDetailVO.getId());

//        if (optionalCartDetail.isPresent()) {
//            Integer checkquantity = cartDetailVO.getQuantity();
//            if (checkquantity > 0) {
//                CartDetail entity = optionalCartDetail.get();
//                BeanUtils.copyProperties(cartDetailVO, entity);
//                cartDao.save(entity);
//            } else {
//                CartDetail entity = optionalCartDetail.get();
//                BeanUtils.copyProperties(cartDetailVO, entity);
//                cartDao.delete(entity);
//            }
//        }
    }
//    @Override
//    public CartDetailVO newCartDetaiilByUsername(Integer idProduct, Principal principal) {
//
//        if(principal == null){
//            System.out.println("bạn chưa đăng nhập");
//            return null;
//        }
//        Optional<Product> oProduct = productDAO.findById(idProduct);
////        Optional<CartDetail> optionalCartDetail = cartDao.findById(idProduct);
////        if (optionalCartDetail.get() == null) {
////
////        }
//        Optional<Account> optionalAccount = accountDAO.findByUsername(principal.getName());
//        CartDetail cartDetail;
//        // cái này là getcartbyidproduct, lười sửa nên comment vào đây.
//        // à sửa r
//        Optional<CartDetail> oCart = cartDao.getInfoCartByIDProductAndUsername(oProduct.get().getId(), optionalAccount.get().getUsername());
//        if (oCart.isPresent()) {
//            cartDetail = oCart.get();
//        } else {
//            cartDetail = new CartDetail();
//            cartDetail.setUserId(optionalAccount.get().getId());
//            Product product1 = productDAO.getById(idProduct);
//            cartDetail.setProductId(product1.getId());
//            cartDetail.setQuantity(1);
//            cartDetail.setPrice(111.00);
//            cartDetail.setSaleId(1);
//        }
////        cartDetail.setQuantity((int) (cartDetail.getQuantity() + oProduct.get().getPrice()));
//        cartDetailDAO.save(cartDetail);
//        System.out.println("done");
//        return null;
////        CartDetail cartDetail = new CartDetail();
////        Optional<CartDetail> optionalCartDetail = cartDao.getInfoProductByID(id, principal);
////        if(optionalCartDetail == null){
////            System.out.println("chưa có sản phẩm này trong giỏ hàng");
////        } else {
////            if(optionalCartDetail.isPresent()) {
////                System.out.println("đã có sản phẩm này trong giỏ hàng");
////                CartDetail entity = optionalCartDetail.get();
////               BeanUtils.copyProperties(cartDetailVO, entity);
////                cartDao.save(entity);
////            }
////        }
//
////        CartDetail cartDetail = new CartDetail();
////        Product product = new Product();
////                 CartDetail cartDetail = cartDao.getInfoProductByID(id, principal).orElseThrow(()
////                         ->
////                         CartDetail ca = new CartDetail();
////
////                         );
//
//
////        return  null;
//    }


}
