package com.poly.datn.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.AccountRoleDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.entity.AccountRole;
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
    AccountRoleDAO accountRoleDAO;

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
        if (principal == null) {
            return null;
        }
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
        String token = jwtUtils.getTokenByUserName(account.getEmail(), 15 * 60 * 1000);
        ResetPassworDTO resetPassworDTO = new ResetPassworDTO();
        BeanUtils.copyProperties(account, resetPassworDTO);
        resetPassworDTO.setToken(token);
        if (resetPassworDTO.getToken() == null) {
            throw new NotFoundException("common.error.not-found");
        }
        refreshTokenList(resetPassworDTO.getToken());
        resetPassworDTOS.add(resetPassworDTO);
        String resetLink = "http://localhost:8080/resetpass?token=" + resetPassworDTO.getToken();
        System.out.println(resetLink);
        sendMail.sentResetPasswordMail(email, resetLink, account.getFullname());
        System.out.println("list reset" + resetPassworDTOS);
    }

    public Boolean checkToken(String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            return false;
        }
        for (ResetPassworDTO resetPassworDTO : resetPassworDTOS) {
            if (jwtUtils.getUserNameFromJwtToken(token).equals(jwtUtils.getUserNameFromJwtToken(resetPassworDTO.getToken()))
                    && jwtUtils.getIssuedAtFromJwtToken(token).getTime() == jwtUtils.getIssuedAtFromJwtToken(resetPassworDTO.getToken()).getTime()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean create(AccountVO accountVO) throws MessagingException, UnsupportedEncodingException {
        Account account = accountDAO.findAccountByUsername(accountVO.getUsername());
        if (account != null) {
            return false;
        }
        account = accountDAO.findOneByEmail(accountVO.getEmail());
        if (account != null) {
            return false;
        }
        account = new Account();
        BeanUtils.copyProperties(accountVO, account);
        account.setId(null);
        account.setUserStatus(true);
        account = accountDAO.save(account);
        AccountRole accountRole = new AccountRole();
        accountRole.setAccountId(account.getId());
        accountRole.setRoleId(3);
        accountRoleDAO.save(accountRole);
        sendMail.sentMailRegister(account.getEmail(), account.getFullname());
        return true;
    }


    @Override
    public Boolean changePassword(ResetPassworDTO resetPassworDTO) {
        if (checkToken(resetPassworDTO.getToken())) {
            String email = jwtUtils.getUserNameFromJwtToken(resetPassworDTO.getToken());
            Account account = accountDAO.findOneByEmail(email);
            account.setPassword(resetPassworDTO.getPassword());
            accountDAO.save(account);
            refreshTokenList(resetPassworDTO.getToken());
            return true;
        }
        return false;

    }

    public void refreshTokenList(String token) {
        if (resetPassworDTOS.size() > 0) {
            for (ResetPassworDTO resetPassworDTO : resetPassworDTOS) {
                if (!jwtUtils.validateJwtToken(resetPassworDTO.getToken())) {
                    resetPassworDTOS.remove(resetPassworDTO);
                    continue;
                }
                if (jwtUtils.getUserNameFromJwtToken(resetPassworDTO.getToken()).equals(jwtUtils.getUserNameFromJwtToken(token))) {
                    resetPassworDTOS.remove(resetPassworDTO);
                }
            }
        }
    }

    public void updatePassword(ResetPassworDTO resetPassworDTO) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encoderPass = passwordEncoder.encode();
//        resetPassworDTO.setPassword(encoderPass);
//        resetPassworDTO.setPasswordresetKey(null);
    }
}
