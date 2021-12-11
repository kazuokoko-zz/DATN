package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.ReportService;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/report")
public class ReportController {

    @Autowired
    ReportService reportService;


    @GetMapping("unconfirm")
    public ResponseEntity<ResponseDTO<Object>> getListUnConfirmOrders(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.getListUnComfirmOrders(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getCountOrder")
    public ResponseEntity<ResponseDTO<Object>> getCountOrder(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.sumOrderInMonth(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("trending")
    public ResponseEntity<ResponseDTO<Object>> getTrending(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.getTrendingAdmin(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getCancerCountOrder")
    public ResponseEntity<ResponseDTO<Object>> getCancerCountOrder(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.sumCancerOrderInMonth(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("getConfimCountOrder")
    public ResponseEntity<ResponseDTO<Object>> getConfimCountOrder(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.sumComfimOrder(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getComfimCountOrder")
    public ResponseEntity<ResponseDTO<Object>> getComfimCountOrder(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.sumSuccessOrderInMonth(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
