package com.poly.datn.service;

import com.poly.datn.vo.PayInfoVO;
import com.poly.datn.vo.PaymentVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

public interface OnlinePayService {
    String doPay(PayInfoVO payInfoVO, HttpServletRequest request) throws IOException, ParseException;

    PaymentVO getResult(Integer id) throws IOException;

    Object merchantcall(HttpServletRequest request);

    PaymentVO getPayDetail(String tranno, String trandate);
}
