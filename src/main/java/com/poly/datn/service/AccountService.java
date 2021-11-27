package com.poly.datn.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.poly.datn.entity.Account;
import com.poly.datn.jwt.dto.ResetPassworDTO;
import com.poly.datn.vo.AccountVO;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

public interface AccountService {

     AccountVO updateAccount(JsonNode jsonNode, Principal principal);

    AccountVO findByUsername(Principal principal);

    AccountVO changePassword();

    void updateResetPasswordToken(String email) throws MessagingException, UnsupportedEncodingException;

    void checkToken(String token);
//    void updatePassword(String email);
}
