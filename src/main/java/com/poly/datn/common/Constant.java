package com.poly.datn.common;

import org.springframework.beans.factory.annotation.Value;

public class Constant {
    private Constant() {
    }


    public static final String CROSS_ORIGIN = "*";
    public static final String NOT_LOGGED_IN = "bạn chưa đăng nhập";
    public static final String RESPONSEDTO_CODE = "1000";
    public static final String RESPONSEDTO_MESS = "OK";


    //    public static String vnp_Returnurl = "https://javamahtest.herokuapp.com/api/customer/pay/getresult";
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_TmnCode = "CU4DW0O9";
    public static String vnp_HashSecret = "GDCQYIGHEHQJYNLGDUAAZXLEPXKBIHOT";
//    public static String vnp_apiUrl = "https://javamahtest.herokuapp.com/api/customer/pay/merchantipn";

    public static String vnp_Command = "pay";
    public static String vnp_CurrCode = "VND";
    public static String vnp_Locale = "vn";
    public static String vnp_OrderType = "130000";
    public static String vnp_Version = "2.1.0";
}
