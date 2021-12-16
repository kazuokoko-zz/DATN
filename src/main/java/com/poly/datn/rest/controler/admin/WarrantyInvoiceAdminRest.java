package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.WarrantyInvoiceService;
import com.poly.datn.vo.WarrantyInvoiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/winvoice")
public class WarrantyInvoiceAdminRest {
    @Autowired
    WarrantyInvoiceService warrantyInvoiceService;

    @GetMapping("list")
    public ResponseEntity<List<WarrantyInvoiceVO>> getList(Principal principal){
        return ResponseEntity.ok().body(warrantyInvoiceService.getAll(principal));
    }
}
