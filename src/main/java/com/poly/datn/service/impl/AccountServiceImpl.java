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
import com.poly.datn.service.AccountRoleService;
import com.poly.datn.service.AccountService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.vo.VoBoSung.Account.*;
import freemarker.template.TemplateException;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    @Autowired
    AccountRoleService accountRoleService;


    public final List<ResetPassworDTO> resetPassworDTOS = new LinkedList<>();

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
    public NewAccountUserAdmin createAdmin(NewAccountUserAdmin newAccountUserAdmin, Principal principal) throws MessagingException, UnsupportedEncodingException {
        checkPrincipal(principal);
        Account account1 = accountDAO.findAccountByUsername(newAccountUserAdmin.getUsername());
        if (account1 != null) {
            throw  new NotImplementedException("Username đã tồn tại");
        }
        account1 = accountDAO.findOneByEmail(newAccountUserAdmin.getEmail());
        if (account1 != null) {
            throw  new NotImplementedException("Đã có tài khoản cho email mày");
        }
        Account account = new Account();
        String password = RandomString.make(8);
        BeanUtils.copyProperties(newAccountUserAdmin, account);
        account.setPassword(password);
        account.setUserStatus(true);
        account =accountDAO.save(account);
        AccountRoleVO accountRoleVO = new AccountRoleVO();
        accountRoleVO.setAccountId(account.getId());
        if (checkRole.isHavePermition(principal.getName(), "Director")) {
            accountRoleVO.setRoleId(2);
        }
        else if (  checkRole.isHavePermition(principal.getName(), "Staff")) {
            accountRoleVO.setRoleId(3);
        }
        accountRoleService.addRole(accountRoleVO);
        sendMail.sentMailRegisterAdmin(account);
        return newAccountUserAdmin;
    }

    @Override
    public boolean updatePassword(ModifyPassworDTO modifyPassworDTO, Principal principal){
        if (principal == null) {
            throw  new NotImplementedException("Chưa đăng nhập");
        }
        Account account = accountDAO.findAccountByUsername(principal.getName());
        if(!modifyPassworDTO.getOldPassWord().equals(account.getPassword())){
            throw  new NotImplementedException("Mật khẩu cũ không chính xác");
        }
        if(modifyPassworDTO.getNewPassWord().equals(modifyPassworDTO.getOldPassWord())){
            throw  new NotImplementedException("Mật khẩu cũ không được trùng với mật khẩu mỡi");
        }
        if(!modifyPassworDTO.getReNewPassWord().equals(modifyPassworDTO.getNewPassWord())){
            throw  new NotImplementedException("Nhập lại mật khẩu không chính xác");
        }
        account.setPassword(modifyPassworDTO.getNewPassWord());
        accountDAO.save(account);
        return true;
    };

    @Override
    public boolean deleteAccount(Integer id, Principal principal) {
        checkPrincipal(principal);
        Account account1 = accountDAO.findOneById(id);
        if(account1.getUserStatus().equals(false)){
            throw  new NotImplementedException("Tài khoản này đã bị khóa rồi");
        }
        Account account = accountDAO.findAccountByUsername(principal.getName());
        if (checkRole.isHavePermition(principal.getName(), "Director")) {
            if(account.getUsername().equals("admin") && !account1.getUsername().equals(account.getUsername())){
                account1.setUserStatus(false);
                accountDAO.save(account1);
                return true;
            }
            if(!account.getUsername().equals("admin") && account1.getUsername().equals("admin")){
                throw  new NotImplementedException("Không thể xóa tài khoản admin");
            }
            else if(checkRole.isHavePermition(account1.getUsername(), "Director")
                    || account.getUsername().equals(account1.getUsername())){
                throw  new NotImplementedException("Không thể xóa tài khoản admin này");
            }
            else {
                account1.setUserStatus(false);
                accountDAO.save(account1);
                return true;
            }
        }
        else if (checkRole.isHavePermition(principal.getName(), "Staff")) {
            if(checkRole.isHavePermition(account1.getUsername(), "User")){
                account1.setUserStatus(false);
                accountDAO.save(account1);
                return true;
            } else {
                throw  new NotImplementedException("Không có quyền xóa tài khoản admin và staff");
            }
        }
        else {
            throw  new NotImplementedException("Không có quyền xóa tài khoản admin và staff");
        }
    }
    @Override
    public boolean openAccount(Integer id, Principal principal) {
        checkPrincipal(principal);
        Account account1 = accountDAO.findOneById(id);
        if(account1.getUserStatus().equals(true)){
            throw  new NotImplementedException("Tài khoản này không khóa");
        }
        Account account = accountDAO.findAccountByUsername(principal.getName());
        if (checkRole.isHavePermition(principal.getName(), "Director")) {
            if(account.getUsername().equals("admin") && !account1.getUsername().equals(account.getUsername())){
                account1.setUserStatus(true);
                accountDAO.save(account1);
                return true;
            }
            if(!account.getUsername().equals("admin") && account1.getUsername().equals("admin")){
                throw  new NotImplementedException("Tài khoản admin không thể bị khóa, nếu thấy tk admin bị " +
                        "                                           khóa, liên hệ ngay kỹ thuật viên");
            }
            else if(checkRole.isHavePermition(account1.getUsername(), "Director")
                    || account.getUsername().equals(account1.getUsername())){
                throw  new NotImplementedException("Không thể mở tài khoản admin này");
            }
            else {
                account1.setUserStatus(true);
                accountDAO.save(account1);
                return true;
            }
        }
        else if (checkRole.isHavePermition(principal.getName(), "Staff")) {
            if(checkRole.isHavePermition(account1.getUsername(), "User")){
                account1.setUserStatus(true);
                accountDAO.save(account1);
                return true;
            } else {
                throw  new NotImplementedException("Không có quyền mở tài khoản admin và staff");
            }
        }
        else {
            throw  new NotImplementedException("Không có quyền mở tài khoản admin và staff");
        }
    }

    @Override
    public AccountVO updateAccount(JsonNode jsonNode, Principal principal) {
        if (principal == null) {
            throw  new NotImplementedException("Chưa đăng nhập");
        }
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
        if (principal == null) {
            throw  new NotImplementedException("Chưa đăng nhập");
        }
        AccountVO accountVO = new AccountVO();
        Account account = accountDAO.findAccountByUsername(principal.getName());
        BeanUtils.copyProperties(account, accountVO);
        return accountVO;
    }

    @Override
    public List<AccountVO> findAll(Principal principal) {
        checkPrincipal(principal);
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
        checkPrincipal(principal);
        Account account = accountDAO.findById(id).orElse(null);
        if (account == null)
            throw  new NotImplementedException("Không tồn tại tài khoản này");
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
            throw  new NotImplementedException("Không tồn tại tài khoản này");
        }
        String token = jwtUtils.getTokenByUserName(account.getEmail(), 15 * 60 * 1000);
        ResetPassworDTO resetPassworDTO = new ResetPassworDTO();
        BeanUtils.copyProperties(account, resetPassworDTO);
        resetPassworDTO.setToken(token);
        if (resetPassworDTO.getToken() == null) {
            throw  new NotImplementedException("Đã xảy ra lỗi, không thể tạo yêu cầu đổi mật khẩu, vui lòng thử lại sau 15 phút");
        }
        refreshTokenList(resetPassworDTO.getToken());
        resetPassworDTOS.add(resetPassworDTO);
        String resetLink = "http://150.85.105.29/resetpass?token=" + resetPassworDTO.getToken();
        sendMail.sentResetPasswordMail(email, resetLink, account.getFullname());
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
        Account account = accountDAO.findAccountByUsername(accountRegisterVO.getUsername());
        if (account != null) {
            throw  new NotImplementedException("Username đã tồn tại");
        }
        account = accountDAO.findOneByEmail(accountRegisterVO.getEmail());
        if (account != null) {
            throw  new NotImplementedException("Đã có tài khoản cho email mày");
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
        try {
            if (checkToken(resetPassworDTO.getToken())) {
                String email = jwtUtils.getUserNameFromJwtToken(resetPassworDTO.getToken());
                Account account = accountDAO.findOneByEmail(email);
                account.setPassword(resetPassworDTO.getPassword());
                accountDAO.save(account);
                refreshTokenList(resetPassworDTO.getToken());
                return true;
            }
            return false;
        } catch (Exception e){
            throw  new NotImplementedException("Đổi mật khẩu không thành công, vui lòng thử lại sau");
        }


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

}
