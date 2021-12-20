package com.poly.datn.rest.controler;

import com.poly.datn.common.Constant;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.RoleDAO;
import com.poly.datn.entity.Account;
import com.poly.datn.jwt.dto.JwtResponse;
import com.poly.datn.jwt.dto.LoginRequest;
import com.poly.datn.jwt.JwtUtils;
import com.poly.datn.jwt.UserDetailsImpl;
import com.poly.datn.jwt.dto.ResetPassworDTO;
import com.poly.datn.service.AccountService;
import com.poly.datn.vo.ResponseDTO;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/authentication")
public class AuthRest {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Account account = accountDAO.findAccountByUsername(loginRequest.getUsername());
        if(account == null){
            throw new NotImplementedException("Tài khoản không tồn tại, vui lòng đăng ký");
        }
        if(account.getUserStatus().equals(false)){
            throw new NotImplementedException("Tài khoản đã bị khóa, vui lòng liên hệ quản trị viên để biết lý do!");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @GetMapping("/logout")
    public Boolean unAuthenticateUser() {
        return true;
    }

    @PutMapping("/forgot")
    public ResponseEntity<ResponseDTO<Object>> updatepassforgot(@RequestBody ResetPassworDTO resetPassworDTO) {
        return ResponseEntity.ok(ResponseDTO.builder().object(accountService.changePassword(resetPassworDTO))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

}
