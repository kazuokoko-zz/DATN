package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.BlogService;
import com.poly.datn.vo.BlogVO;
import com.poly.datn.vo.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.Optional;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/blog")
public class BlogAdminRest {

    @Autowired
    BlogService blogService;

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<Object>> getOne(@PathVariable("id") Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(blogService.getOneByIdAdmin(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Object>> getList(@RequestParam("pid") Optional<Integer> pid, @RequestParam("title") Optional<String> title, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(blogService.getListAdmin(pid, title, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDTO<Object>> delete(@PathVariable("id") Optional<Integer> id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(blogService.deleteById(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @GetMapping("getLisDeleteAdmin")
    public ResponseEntity<ResponseDTO<Object>> getLisDeleteAdmin(@RequestParam("pid") Optional<Integer> pid, @RequestParam("title") Optional<String> title, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(blogService.getListDeleteAdmin(pid, title,principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("new")
    public ResponseEntity<ResponseDTO<Object>> create(@RequestBody BlogVO blogVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(blogService.create(blogVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ResponseDTO<Object>> updateBlog(@RequestBody BlogVO blogVO, @PathVariable("id") Optional<Integer> id, Principal principal) throws ParseException {
        return ResponseEntity.ok(ResponseDTO.builder().object(blogService.update(blogVO, id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
