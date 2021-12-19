package com.poly.datn.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.poly.datn.jwt.dto.ResetPassworDTO;
import com.poly.datn.vo.VoBoSung.Account.AccountRegisterVO;
import com.poly.datn.vo.VoBoSung.Account.AccountVO;
import com.poly.datn.vo.VoBoSung.Account.ModifyPassworDTO;
import com.poly.datn.vo.VoBoSung.Account.NewAccountUserAdmin;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

public interface AccountService {

    boolean deleteAccount(Integer id, Principal principal);
    boolean openAccount(Integer id, Principal principal);

    AccountVO updateAccount(JsonNode jsonNode, Principal principal);

    AccountVO findByUsername(Principal principal);

    Boolean changePassword(ResetPassworDTO resetPassworDTO);

    List<AccountVO> findAll(Principal principal);

    void updateResetPasswordToken(String email) throws MessagingException, IOException, TemplateException;

    Boolean checkToken(String token);

    AccountRegisterVO create(AccountRegisterVO accountRegisterVO) throws MessagingException, UnsupportedEncodingException;
    NewAccountUserAdmin createAdmin(NewAccountUserAdmin newAccountUserAdmin, Principal principal) throws MessagingException, UnsupportedEncodingException;

    AccountVO findByUsernameAdmin(Integer id, Principal principal);

    Boolean checkEmail(String email);
    Boolean checkUsername(String username);
    boolean updatePassword(ModifyPassworDTO modifyPassworDTO, Principal principal);
}
