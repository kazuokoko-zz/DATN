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
import java.text.ParseException;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/pay")
public class OnlinePayRest {
    @Autowired
    OnlinePayService onlinePayService;

    @PostMapping("getpayurl")
    public ResponseEntity<ResponseDTO<Object>> doPay(@RequestBody PayInfoVO payInfoVO, HttpServletRequest request) throws IOException, ParseException {
        return ResponseEntity.ok(ResponseDTO.builder().object(onlinePayService.doPay(payInfoVO, request))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("merchantipn")
    public ResponseEntity<ResponseDTO<Object>> merchantIpn(HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(ResponseDTO.builder().object(onlinePayService.merchantcall(request))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getpaydetail")
    public ResponseEntity<ResponseDTO<Object>> getPayDetail(@RequestParam("tranno") String tranno, @RequestParam("trandate") String trandate) throws IOException {
        return ResponseEntity.ok(ResponseDTO.builder().object(onlinePayService.getPayDetail(tranno, trandate))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getresult/{id}")
    public ResponseEntity<ResponseDTO<Object>> getResult(
            @PathVariable Integer id,
            HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(ResponseDTO.builder()
                .object(onlinePayService.getResult(id, request)
                )
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}

