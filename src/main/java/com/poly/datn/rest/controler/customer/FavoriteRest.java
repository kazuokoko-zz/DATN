package com.poly.datn.rest.controler.customer;

import com.poly.datn.common.Constant;
import com.poly.datn.service.CartDetailService;
import com.poly.datn.service.FavoriteService;
import com.poly.datn.vo.FavoriteVO;
import com.poly.datn.vo.ResponseDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/customer/favorite")
public class FavoriteRest {


    @Autowired
    private FavoriteService favoriteServices;

    @GetMapping("get")
    public ResponseEntity<ResponseDTO<Object>> getList(Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(favoriteServices.getFavorites(principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PostMapping("add")
    public ResponseEntity<ResponseDTO<Object>> addToList(@RequestBody FavoriteVO favoriteVO,Principal principal){
        return ResponseEntity.ok(ResponseDTO.builder().object(favoriteServices.addToFavorite(favoriteVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
