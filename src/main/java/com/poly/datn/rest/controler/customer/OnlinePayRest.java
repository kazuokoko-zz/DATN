package com.poly.datn.rest.controler.customer;

import com.poly.datn.common.Constant;
import com.poly.datn.service.OnlinePayService;
import com.poly.datn.vo.PayInfoVO;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/pay")
public class OnlinePayRest {
    @Autowired
    OnlinePayService onlinePayService;

    @GetMapping("getpayurl")
    public ResponseEntity<ResponseDTO<Object>> doPay(@RequestBody PayInfoVO payInfoVO, HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(ResponseDTO.builder().object(onlinePayService.doPay(payInfoVO, request))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("merchantipn")
    public ResponseEntity<ResponseDTO<Object>> merchantIpn(HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(ResponseDTO.builder().object(onlinePayService.merchantcall(request))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getresult")
    public ResponseEntity<ResponseDTO<Object>> getResult(
//            @PathVariable Integer id,
            HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(ResponseDTO.builder()
//                .object(onlinePayService.getResult(id, request)
                .object(onlinePayService.getResult(1, request)
                )
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
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

