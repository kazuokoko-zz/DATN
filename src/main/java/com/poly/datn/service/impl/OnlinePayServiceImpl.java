package com.poly.datn.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.poly.datn.common.Constant;
import com.poly.datn.config.VnpayConfig;
import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.CustomerDAO;
import com.poly.datn.dao.OrdersDAO;
import com.poly.datn.dao.PaymentDAO;
import com.poly.datn.entity.Customer;
import com.poly.datn.entity.Orders;
import com.poly.datn.entity.Payment;
import com.poly.datn.service.OnlinePayService;
import com.poly.datn.vo.PayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OnlinePayServiceImpl implements OnlinePayService {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    OrdersDAO ordersDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    PaymentDAO paymentDAO;

    @Override
    @Transactional
    public String doPay(PayInfoVO payInfoVO, HttpServletRequest request) throws IOException {

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());

        Payment payment = new Payment();
        Customer customer = customerDAO.getById(ordersDAO.getById(payInfoVO.getOrdersId()).getCustomerId());

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = payInfoVO.getOrderInfo();
        String orderType = "130000";//payInfoVO.getOrderType();
        String vnp_TxnRef;
        Boolean exists = false;
        do {
            vnp_TxnRef = VnpayConfig.getRandomNumber(8);
            for (Payment pm : paymentDAO.findAllByTxnRef(vnp_TxnRef)) {
                if (pm.getCreateDate().substring(0, 8).equals(formatter.format(cld.getTime()).substring(0, 8))) {
                    if (pm.getOrdersId() == payInfoVO.getOrdersId()) {
                        exists = true;
                        break;
                    }
                }
            }
        } while (exists);

        Payment pa = paymentDAO.getByOrdersIdToday(payInfoVO.getOrdersId(), vnp_CreateDate.substring(0, 8));

        payment.setBankCode(null);
        payment.setBankTranNo(null);
        payment.setCardType(null);
        payment.setCreateDate(vnp_CreateDate);
        payment.setOrderInfo(payInfoVO.getOrderInfo());
        payment.setOrdersId(payInfoVO.getOrdersId());
        payment.setTransactionNo(null);
        payment.setTxnRef(vnp_TxnRef);
        payment.setStatus(-1);

        if (paymentDAO.save(payment) == null)
            return null;

        String vnp_IpAddr = VnpayConfig.getIpAddress(request);
        String vnp_TmnCode = Constant.vnp_TmnCode;

        int amount = payInfoVO.getAmount() * 100;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
//        String bank_code = payInfoVO.getBankcode();
//        if (bank_code != null && !bank_code.isEmpty()) {
//            vnp_Params.put("vnp_BankCode", bank_code);
//        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = payInfoVO.getLanguage();
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Constant.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);


        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        //Billing

        vnp_Params.put("vnp_Bill_Mobile", customer.getPhone());
        vnp_Params.put("vnp_Bill_Email", customer.getEmail());
        String fullName = customer.getFullname().trim();
        if (fullName != null && !fullName.isEmpty()) {
            int idx = fullName.indexOf(' ');
            String firstName = fullName.substring(0, idx);
            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
            vnp_Params.put("vnp_Bill_FirstName", firstName);
            vnp_Params.put("vnp_Bill_LastName", lastName);

        }
//        vnp_Params.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
//        vnp_Params.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
//        vnp_Params.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
//        if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
//            vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
//        }

        // Invoice

//        vnp_Params.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
//        vnp_Params.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
//        vnp_Params.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
//        vnp_Params.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
//        vnp_Params.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
//        vnp_Params.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
//        vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));


        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(Constant.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Constant.vnp_PayUrl + "?" + queryUrl;
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
////        resp.getWriter().write(gson.toJson(job));
////        return gson.toJson(job);
//        System.out.println(job.get("data"));
//        payment.setBankCode(payInfoVO.getBankcode());

        return paymentUrl;
//        resp.getWriter().write(gson.toJson(job));
    }

    @Override
    public String getResult(Integer id, HttpServletRequest request) throws IOException {
        Payment payment = paymentDAO.getById(id);

        String vnp_TxnRef = payment.getTxnRef();//req.getParameter("order_id");
        String vnp_TransDate = payment.getCreateDate();//req.getParameter("trans_date");
        String vnp_TmnCode = Constant.vnp_TmnCode;
        String vnp_IpAddr = VnpayConfig.getIpAddress(request);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "querydr");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD OrderId:" + vnp_TxnRef);
        vnp_Params.put("vnp_TransDate", vnp_TransDate);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(Constant.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Constant.vnp_apiUrl + "?" + queryUrl;
        URL url = new URL(paymentUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String Rsp = response.toString();
        return paymentUrl;
//        String respDecode = URLDecoder.decode(Rsp, "UTF-8");
//        String[] responseData = respDecode.split("&|\\=");
//        return Arrays.toString(responseData);

    }

    @Override
    public Object merchantcall(HttpServletRequest request) {


        try {

	/*  IPN URL: Record payment results from VNPAY
	Implementation steps:
	Check checksum
	Find transactions (vnp_TxnRef) in the database (checkOrderId)
	Check the payment status of transactions before updating (checkOrderStatus)
	Check the amount (vnp_Amount) of transactions before updating (checkAmount)
	Update results to Database
	Return recorded results to VNPAY
	*/

            // ex:  	PaymnentStatus = 0; pending
            //              PaymnentStatus = 1; success
            //              PaymnentStatus = 2; Faile

            //Begin process return from VNPAY
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = (String) params.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }


            Payment payment = paymentDAO.getByTxnRefToday((String) fields.get("vnp_TxnRef"), ((String) fields.get("vnp_PayDate")).substring(0, 8));


            // Check checksum
            String signValue = VnpayConfig.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {

                boolean checkOrderId = true; // vnp_TxnRef exists in your database
                boolean checkAmount = true; // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
                boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)


                if (checkOrderId) {
                    if (checkAmount) {
                        if (checkOrderStatus) {
                            payment.setBankCode((String) fields.get("vnp_BankCode"));
                            payment.setTransactionNo((String) fields.get("vnp_TransactionNo"));
                            payment.setCardType((String) fields.get("vnp_CardType"));
                            payment.setBankTranNo((String) fields.get("vnp_TransactionNo"));
                            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                                payment.setStatus(1);
                                //Here Code update PaymnentStatus = 1 into your Database
                            } else {
                                payment.setStatus(2);
                                // Here Code update PaymnentStatus = 2 into your Database
                            }
                            paymentDAO.save(payment);
                            return "{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}";
                        } else {

                            return "{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}";
                        }
                    } else {
                        return "{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}";
                    }
                } else {
                    return "{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}";
                }
            } else {
                return "{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}";
            }
        } catch (Exception e) {
            return "{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}";
        }
    }
    //localhost:8080/vnpay_jsp/vnpay_return.jsp?
    // vnp_Amount=30000000
    // &vnp_BankCode=NCB
    // &vnp_BankTranNo=20211030103711
    // &vnp_CardType=ATM
    // &vnp_OrderInfo=thanh+to%3Fn+m%3Fy+t%3Fnh&vnp_PayDate=20211030103708
    // &vnp_ResponseCode=00
    // &vnp_TmnCode=CU4DW0O9
    // &vnp_TransactionNo=13615489
    // &vnp_TransactionStatus=00
    // &vnp_TxnRef=52319937
    // &vnp_SecureHash=b2204e4190ac1fec4f6266f23f87445444b1ee0a80a5d75fc4616eb4819e2ff2e9aa4650dca9ce3c6209dfe7060ee702be1818d7d8f5e879a57d5fbe2681a86d
}
