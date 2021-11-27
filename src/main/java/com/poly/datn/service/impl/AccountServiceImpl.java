package com.poly.datn.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.jwt.JwtUtils;
import com.poly.datn.jwt.dto.ResetPassworDTO;
import com.poly.datn.service.AccountService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.AccountVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDAO accountDAO;

    @Autowired
    SendMail sendMail;

    @Autowired
    CheckRole checkRole;

    @Autowired
    JwtUtils jwtUtils;

    public final List<ResetPassworDTO> resetPassworDTOS = new LinkedList<>();

    @Override
    public AccountVO updateAccount(JsonNode jsonNode, Principal principal) {
        if (principal == null) return null;
        ObjectMapper mapper = new ObjectMapper();

        AccountVO accountVO = mapper.convertValue(jsonNode, AccountVO.class);
        Account account = accountDAO.findAccountByUsername(principal.getName());
        account.setFullname(accountVO.getFullname());
        account.setAddress(accountVO.getAddress());
        account.setPhone(accountVO.getPhone());
        account = accountDAO.save(account);
        BeanUtils.copyProperties(account, accountVO);
        return accountVO;
    }

    @Override
    public AccountVO findByUsername(Principal principal) {
        if (principal == null) return null;
        AccountVO accountVO = new AccountVO();
        Account account = accountDAO.findAccountByUsername(principal.getName());
        BeanUtils.copyProperties(account, accountVO);
        return accountVO;
    }

    @Override
    public List<AccountVO> findAll(Principal principal) {
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))
        ) {
            return null;
        }
        List<Account> accounts = accountDAO.findAll();
        List<AccountVO> accountVOS = new ArrayList<>();
        for (Account account : accounts) {
            AccountVO accountVO = new AccountVO();
            BeanUtils.copyProperties(account, accountVO);
            accountVOS.add(accountVO);
        }
        return accountVOS;
    }


    @Override
    public void updateResetPasswordToken(String email) throws MessagingException, UnsupportedEncodingException {
        Account account = accountDAO.findOneByEmail(email);
        if (account == null) {
            throw new NotFoundException("common.error.not-found");
        }
        if (resetPassworDTOS.size() > 0) {
            for (ResetPassworDTO resetPassworDTO : resetPassworDTOS) {
                if (!jwtUtils.validateJwtToken(resetPassworDTO.getToken())) {
                    resetPassworDTOS.remove(resetPassworDTO);
                    continue;
                }
                if (jwtUtils.getUserNameFromJwtToken(resetPassworDTO.getToken()).equals(account.getEmail())) {
                    resetPassworDTOS.remove(resetPassworDTO);
                }
            }
        }
        String token = jwtUtils.getTokenByUserName(account.getEmail(), 15 * 60 * 1000);
        ResetPassworDTO resetPassworDTO = new ResetPassworDTO();
        BeanUtils.copyProperties(account, resetPassworDTO);
        resetPassworDTO.setToken(token);
        if (resetPassworDTO.getToken() == null) {
            throw new NotFoundException("common.error.not-found");
        }
//        resetPassworDTO.setTimecreate(System.currentTimeMillis());
        resetPassworDTO.setClick(0);
        resetPassworDTOS.add(resetPassworDTO);
        String resetLink = "http://150.95.105.29/change/reset_password?token=" + resetPassworDTO.getToken();
        System.out.println(resetLink);
        sendMail.sentResetPasswordMail(email, resetLink);
        System.out.println("list reset" + resetPassworDTOS);
    }

    public Boolean checkToken(String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            return false;
        }
        for (ResetPassworDTO resetPassworDTO : resetPassworDTOS) {
            if (jwtUtils.getUserNameFromJwtToken(token).equals(jwtUtils.getUserNameFromJwtToken(resetPassworDTO.getToken()))) {
                return true;
            }
        }
        return false;
//        if (resetPassworDTO.size() > 0) {
//            resetPassworDTO.forEach(resetPassworDTO1 -> {
//                if (resetPassworDTO1.getPasswordresetKey().equals(token)) {
//                    Long timeNow = System.currentTimeMillis();
//                    Long timeCreate = resetPassworDTO1.getTimecreate();
//                    //900000
//                    if (timeNow - timeCreate > 90) {
//                        resetPassworDTO.remove(resetPassworDTO1);
//                        throw new NotFoundException("common.error.not-found");
//                    }
//                    resetPassworDTO1.setClick(1);
//                    resetPassworDTO.add(resetPassworDTO1);
//                    System.out.println("danh sach token " + resetPassworDTO);
//                } else {
//                    throw new NotFoundException("common.error.not-found");
//                }
//
//            });
//        } else {
//            throw new NotFoundException("common.error.not-found");
//        }
    }


    @Override
    public Boolean changePassword(ResetPassworDTO resetPassworDTO) {
        if (checkToken(resetPassworDTO.getToken())) {
            String email = jwtUtils.getUserNameFromJwtToken(resetPassworDTO.getToken());
            Account account = accountDAO.findOneByEmail(email);
            account.setPassword(resetPassworDTO.getPassword());
            accountDAO.save(account);
//            accountDAO.changePass(resetPassworDTO.getPassword(), email);
            return true;
        }
        return false;

    }

    public void updatePassword(ResetPassworDTO resetPassworDTO) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encoderPass = passwordEncoder.encode();
//        resetPassworDTO.setPassword(encoderPass);
//        resetPassworDTO.setPasswordresetKey(null);
    }
}
