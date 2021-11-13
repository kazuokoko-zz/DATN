package com.poly.datn.service;

import com.poly.datn.vo.PayInfoVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

public interface OnlinePayService {
    String doPay(PayInfoVO payInfoVO, HttpServletRequest request) throws IOException, ParseException;

    Boolean getResult(Integer id, HttpServletRequest request) throws IOException;

    Object merchantcall(HttpServletRequest request);
}
