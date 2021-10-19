package com.poly.datn.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.poly.datn.vo.AccountVO;

import java.security.Principal;

public interface AccountService {

     AccountVO updateAccount(JsonNode jsonNode, Principal principal);

    AccountVO findByUsername(Principal principal);
}
