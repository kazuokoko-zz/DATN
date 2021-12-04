package com.poly.datn.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.poly.datn.jwt.dto.ResetPassworDTO;
import com.poly.datn.vo.AccountVO;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

public interface AccountService {

    AccountVO updateAccount(JsonNode jsonNode, Principal principal);

    AccountVO findByUsername(Principal principal);

    Boolean changePassword(ResetPassworDTO resetPassworDTO);

    List<AccountVO> findAll(Principal principal);

    void updateResetPasswordToken(String email) throws MessagingException, IOException, TemplateException;

    Boolean checkToken(String token);

    Boolean create(AccountVO accountVO) throws MessagingException, UnsupportedEncodingException;

    AccountVO findByUsernameAdmin(Integer id, Principal principal);
//    void updatePassword(String email);
}
