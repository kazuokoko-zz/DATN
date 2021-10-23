package com.poly.datn.rest.controler.customer;

import com.poly.datn.common.Constant;
import com.poly.datn.service.BlogService;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/blog")
public class BlogRest {

    @Autowired
    BlogService blogService;

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<Object>> getOne(@PathVariable("id") Integer id){
        return ResponseEntity.ok(ResponseDTO.builder().object(blogService.getById(id))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Object>> getList(@RequestParam("pid") Optional<Integer> pid,@RequestParam("title") Optional<String> title){
        return ResponseEntity.ok(ResponseDTO.builder().object(blogService.getList(pid,title))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }


}
