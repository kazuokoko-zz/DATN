package com.poly.datn.service.impl;

import com.poly.datn.common.Constant;
import com.poly.datn.config.VnpayConfig;
import com.poly.datn.dao.CustomerDAO;
import com.poly.datn.dao.OrderManagementDAO;
import com.poly.datn.dao.OrdersDAO;
import com.poly.datn.dao.PaymentDAO;
import com.poly.datn.entity.Customer;
import com.poly.datn.entity.OrderManagement;
import com.poly.datn.entity.Orders;
import com.poly.datn.entity.Payment;
import com.poly.datn.service.OnlinePayService;
import com.poly.datn.utils.AutoCreate;
import com.poly.datn.vo.PayInfoVO;
import com.poly.datn.vo.PayResponseVO;
import com.poly.datn.vo.PaymentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    @Autowired
    OrderManagementDAO orderManagementDAO;

    @Override
    @Transactional
    public String doPay(PayInfoVO payInfoVO, HttpServletRequest request) throws IOException, ParseException {


//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());

        Payment payment = new Payment();
        Orders orders = ordersDAO.findById(payInfoVO.getOrdersId()).get();
        Customer customer = customerDAO.getById(orders.getCustomerId());

        String vnp_OrderInfo = payInfoVO.getOrderInfo();
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
        if (pa != null) {
            if (pa.getStatus() == 0) {
                Date dn = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7")).getTime();
                Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                cal1.setTime(formatter.parse(pa.getCreateDate()));
                cal1.add(Calendar.MINUTE, 15);
                if (dn.after(cal1.getTime())) {
                    paymentDAO.deleteById(pa.getId());
                } else {
                    return null;
                }
            } else if (pa.getStatus() == 1) {
                return null;
            } else {
                paymentDAO.deleteById(pa.getId());
            }
        }
        payment.setBankCode(null);
        payment.setBankTranNo(null);
        payment.setCardType(null);
        payment.setCreateDate(vnp_CreateDate);
        payment.setOrderInfo(payInfoVO.getOrderInfo());
        payment.setOrdersId(payInfoVO.getOrdersId());
        payment.setTransactionNo(null);
        payment.setTxnRef(vnp_TxnRef);
        payment.setStatus(0);

        if (paymentDAO.save(payment) == null)
            return null;

        String vnp_IpAddr = VnpayConfig.getIpAddress(request);
        String vnp_TmnCode = Constant.vnp_TmnCode;

        Long amount = orders.getSumprice() * 100;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Constant.vnp_Version);
        vnp_Params.put("vnp_Command", Constant.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", Constant.vnp_CurrCode);

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", Constant.vnp_OrderType);

        vnp_Params.put("vnp_Locale", Constant.vnp_Locale);

        vnp_Params.put("vnp_ReturnUrl", payInfoVO.getReturnURL());
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


        return paymentUrl;
    }

    @Override
    public Boolean getResult(Integer id, HttpServletRequest request) throws IOException {
        Payment payment = paymentDAO.getByOrdersIdEquals(id);

        return payment != null ? payment.getStatus() == 1 ? true : false : false;

    }

    @Override
    public Object merchantcall(HttpServletRequest request) {
        System.out.println("Ipn da duoc goi");
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
            Orders orders = ordersDAO.findById(payment.getOrdersId()).get();

            // Check checksum
            String signValue = VnpayConfig.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {

                boolean checkOrderId = payment.getTxnRef().equalsIgnoreCase((String) fields.get("vnp_TxnRef")); // vnp_TxnRef exists in your database
                boolean checkAmount = (orders.getSumprice() * 100) == Long.parseLong((String) fields.get("vnp_Amount")); // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
                boolean checkOrderStatus = payment.getStatus() == 0; // PaymnentStatus = 0 (pending)


                if (checkOrderId) {
                    if (checkAmount) {
                        if (checkOrderStatus) {
                            payment.setBankCode((String) fields.get("vnp_BankCode"));
                            payment.setTransactionNo((String) fields.get("vnp_TransactionNo"));
                            payment.setCardType((String) fields.get("vnp_CardType"));
                            payment.setBankTranNo((String) fields.get("vnp_TransactionNo"));
                            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                                payment.setStatus(1);

                            } else {
                                payment.setStatus(2);
                            }
                            paymentDAO.save(payment);
                            OrderManagement orderManagement = AutoCreate
                                    .createOrderManagement(payment.getOrdersId(),
                                            "Đã thanh toán", "sys");
                            if (payment.getStatus() == 1) {
                                orderManagementDAO.save(orderManagement);
                            }
                            return new PayResponseVO("00", "Confirm Success");
                        } else {
                            return new PayResponseVO("02", "Order already confirmed");
                        }
                    } else {
                        return new PayResponseVO("04", "Invalid Amount");
                    }
                } else {
                    return new PayResponseVO("01", "Order not Found");
                }
            } else {
                return new PayResponseVO("97", "Invalid Checksum");
            }
        } catch (Exception e) {
            return new PayResponseVO("99", "Unknow error");
        }
    }

    @Override
    public PaymentVO getPayDetail(String tranno, String trandate) {
        Payment payment = paymentDAO.getByTranNoAndTranDate(tranno,trandate);
        PaymentVO paymentVO = new PaymentVO();
        BeanUtils.copyProperties(payment,paymentVO);
        return paymentVO;
    }
}
