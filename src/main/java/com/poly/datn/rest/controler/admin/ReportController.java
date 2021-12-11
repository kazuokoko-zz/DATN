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

    @GetMapping("trending")
    public ResponseEntity<ResponseDTO<Object>> getTrending(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.getTrendingAdmin(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("getCountOrder")
    public ResponseEntity<ResponseDTO<Object>> getCountOrder(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.sumOrderInMonth(principal))
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
    @GetMapping("getErrorCountOrder")
    public ResponseEntity<ResponseDTO<Object>> getErrorCountOrder(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.sumErrorOrder(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getSuccessComfimCountOrder")
    public ResponseEntity<ResponseDTO<Object>> getSuccessComfimCountOrder(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.sumSuccessOrderInMonth(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }




    @GetMapping("getlistOrders")
    public ResponseEntity<ResponseDTO<Object>> getlistOrders(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.getlistOrders(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("getListCancerOrders")
    public ResponseEntity<ResponseDTO<Object>> getListCancerOrders(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.getListCancerOrders(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("getListComfimOrders")
    public ResponseEntity<ResponseDTO<Object>> getListComfimOrders(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.getListComfimOrders(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("getListErrorOrders")
    public ResponseEntity<ResponseDTO<Object>> getListErrorOrders(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.getListErrorOrders(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("getListSuccessOrders")
    public ResponseEntity<ResponseDTO<Object>> getListSuccessOrders(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(reportService.getListSuccessOrders(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

}
