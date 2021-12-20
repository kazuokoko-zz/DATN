package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.utils.PrintUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/print")
public class PrintAdminRest {
    @Autowired
    PrintUtils printUtils;

    @GetMapping("order/{id}")
    public ResponseEntity<Resource> printOrder(@PathVariable("id") Integer id) {
        Resource resource = printUtils.printOrder(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Hoadon.pdf\"").body(resource);
    }
    @GetMapping("payment/{id}")
    public ResponseEntity<Resource> printPayment(@PathVariable("id") Integer id) {
        Resource resource = printUtils.printPayment(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Thongtinthanhtoan.pdf\"").body(resource);
    }
    @GetMapping("warranty/{id}")
    public ResponseEntity<Resource> printWarranty(@PathVariable("id") Integer id) {
        Resource resource = printUtils.printWarranty(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Phieubaohanh.pdf\"").body(resource);
    }
    @GetMapping("warrantyiv/{id}")
    public ResponseEntity<Resource> printWarrantyIv(@PathVariable("id") Integer id) {
        Resource resource = printUtils.printWarrantyIv(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Hoadonbaohanh.pdf\"").body(resource);
    }

}
