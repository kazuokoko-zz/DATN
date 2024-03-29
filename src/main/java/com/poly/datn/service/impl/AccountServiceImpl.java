package com.poly.datn.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.AccountRoleDAO;
import com.poly.datn.dao.RoleDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.entity.AccountRole;
import com.poly.datn.entity.Role;
import com.poly.datn.jwt.JwtUtils;
import com.poly.datn.jwt.dto.ResetPassworDTO;
import com.poly.datn.service.AccountService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.AccountRegisterVO;
import com.poly.datn.vo.AccountVO;
import freemarker.template.TemplateException;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.IOException;
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
    RoleDAO roleDAO;

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
            accountVO.setRoles(getRoleforAccount(account.getId()));
            accountVOS.add(accountVO);
        }
        return accountVOS;
    }


    @Override
    public AccountVO findByUsernameAdmin(Integer id, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))
        ) {
            return null;
        }
        Account account = accountDAO.findById(id).orElse(null);
        if (account == null)
            return null;
        AccountVO accountVO = new AccountVO();
        BeanUtils.copyProperties(account, accountVO);
        accountVO.setRoles(getRoleforAccount(account.getId()));
        return accountVO;
    }

    @Override
    public Boolean checkEmail(String email) {
        Account account = accountDAO.findOneByEmail(email);
        if (account != null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkUsername(String username) {
        Account account = accountDAO.findAccountByUsername(username);
        if (account != null) {
            return false;
        }
        return true;
    }

    private List<String> getRoleforAccount(Integer accountId) {
        List<String> roles = new ArrayList<>();
        for (AccountRole accountRole : accountRoleDAO.findAllByAccountIdEquals(accountId)) {
            Role role = roleDAO.getById(accountRole.getRoleId());
            if (role != null)
                roles.add(role.getRoleName());
        }
        return roles;
    }


    @Override
    public void updateResetPasswordToken(String email) throws MessagingException, IOException, TemplateException {
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
    public AccountRegisterVO create(@Valid AccountRegisterVO accountRegisterVO) throws MessagingException, UnsupportedEncodingException {
        if(accountRegisterVO.getUsername() == ""|| accountRegisterVO.getFullname() == ""|| accountRegisterVO.getEmail()== "" || accountRegisterVO.getPassword() == ""){
            throw new NotImplementedException("Tham số không đúng");
        }
        Account account = accountDAO.findAccountByUsername(accountRegisterVO.getUsername());
        if (account != null) {
            throw new DuplicateKeyException("common.error.dupplicate");
        }
        account = accountDAO.findOneByEmail(accountRegisterVO.getEmail());
        if (account != null) {
            throw new DuplicateKeyException("common.error.dupplicate");
        }
        account = new Account();
        BeanUtils.copyProperties(accountRegisterVO, account);
        account.setId(null);
        account.setUserStatus(true);
        account = accountDAO.save(account);
        AccountRole accountRole = new AccountRole();
        accountRole.setAccountId(account.getId());
        accountRole.setRoleId(3);
        accountRoleDAO.save(accountRole);
        sendMail.sentMailRegister(account.getEmail(), account.getFullname());
        return accountRegisterVO;
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
