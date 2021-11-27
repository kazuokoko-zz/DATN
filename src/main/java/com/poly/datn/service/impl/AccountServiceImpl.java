package com.poly.datn.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.datn.jwt.dto.InfoAccount;
import com.poly.datn.jwt.dto.ResetPassworDTO;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.AccountVO;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
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
    public List<ResetPassworDTO> resetPassworDTO = new ArrayList<>();

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
    public AccountVO changePassword() {
        return null;
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
        for (Account account : accounts){
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
        if(resetPassworDTO.size()  > 0) {
            for(ResetPassworDTO resetPassworDTO2 : resetPassworDTO){
                if (resetPassworDTO2.getEmail().equals(email)) {
                    resetPassworDTO.remove(resetPassworDTO2);
                }
            }
        }
        String token = RandomString.make(255);
        ResetPassworDTO resetPassworDTO1 = new ResetPassworDTO();
        BeanUtils.copyProperties(account, resetPassworDTO1);
        resetPassworDTO1.setPasswordresetKey(token);
        if (resetPassworDTO1.getPasswordresetKey() == null) {
            throw new NotFoundException("common.error.not-found");
        }
        resetPassworDTO1.setTimecreate(System.currentTimeMillis());
        resetPassworDTO1.setClick(0);
        resetPassworDTO.add(resetPassworDTO1);
        String resetLink = "http://150.95.105.29/change/reset_password?token=" + resetPassworDTO1.getPasswordresetKey();
        System.out.println(resetLink);
        sendMail.sentMail(email, resetLink);
        System.out.println("list reset" +resetPassworDTO);
    }

    public void checkToken(String token){
        if(resetPassworDTO.size() > 0) {
            resetPassworDTO.forEach(resetPassworDTO1 -> {
                if (resetPassworDTO1.getPasswordresetKey().equals(token)) {
                    Long timeNow = System.currentTimeMillis();
                    Long timeCreate = resetPassworDTO1.getTimecreate();
                    //900000
                    if (timeNow - timeCreate > 90) {
                        resetPassworDTO.remove(resetPassworDTO1);
                        throw new NotFoundException("common.error.not-found");
                    }
                    resetPassworDTO1.setClick(1);
                    resetPassworDTO.add(resetPassworDTO1);
                    System.out.println("danh sach token " + resetPassworDTO);
                } else {
                    throw new NotFoundException("common.error.not-found");
                }

            });
        } else {
            throw new NotFoundException("common.error.not-found");
        }

    }
    public void updatePassword(ResetPassworDTO resetPassworDTO, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoderPass = passwordEncoder.encode(newPassword);
        resetPassworDTO.setPassword(encoderPass);
        resetPassworDTO.setPasswordresetKey(null);
    }
}
