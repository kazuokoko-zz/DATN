package com.poly.datn.rest.controler.admin;

import com.poly.datn.common.Constant;
import com.poly.datn.service.OrdersService;
import com.poly.datn.vo.OrdersVO;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.VoBoSung.NoteOrderManagementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/admin/orders")
public class OrdersAdminRest {

    @Autowired
    OrdersService ordersService;

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO<Object>> getOrders(Principal principal, @PathVariable("id") Integer id) throws NullPointerException, SecurityException {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.getByIdAndUserNameAdmin(id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO<Object>> getList(Principal principal,
                                                       @RequestParam("id") Optional<Integer> id,
                                                       @RequestParam("email") Optional<String> email,
                                                       @RequestParam("name") Optional<String> name,
                                                       @RequestParam("phone") Optional<String> phone) throws NullPointerException, SecurityException {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.getList(principal, id, email, name, phone))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("new")
    public ResponseEntity<ResponseDTO<Object>> newOrder(@RequestBody OrdersVO ordersVO, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.newOrderAdmin(ordersVO, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PutMapping("update")
    public ResponseEntity<ResponseDTO<Object>> updateOrder(@RequestParam("id") Optional<Integer> id, @RequestParam("status") Optional<String> status, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.updateOrderAdmin(id, status, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }



    @PostMapping("cancerOrder")
    public ResponseEntity<ResponseDTO<Object>> cancerOrder(@RequestBody NoteOrderManagementVo noteOrderManagementVo,@RequestParam Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.cancerOrder(noteOrderManagementVo, id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PostMapping("confimOrder")
    public ResponseEntity<ResponseDTO<Object>> confimOrder(@RequestBody NoteOrderManagementVo noteOrderManagementVo, @RequestParam Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.confimOrder(noteOrderManagementVo,id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @PostMapping("confimTransport")
    public ResponseEntity<ResponseDTO<Object>> confimTransport(@RequestBody NoteOrderManagementVo noteOrderManagementVo,@RequestParam Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.confimTransport(noteOrderManagementVo, id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

//    @PostMapping("requestReturns")
//    public ResponseEntity<ResponseDTO<Object>> requestReturns(@RequestBody NoteOrderManagementVo noteOrderManagementVo,@RequestParam Integer id, Principal principal) {
//        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.requestReturns(noteOrderManagementVo, id, principal))
//                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
//    }
//    @PostMapping("comfimReturns")
//    public ResponseEntity<ResponseDTO<Object>> comfimReturns(@RequestBody NoteOrderManagementVo noteOrderManagementVo,@RequestParam Integer id, Principal principal) {
//        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.comfimReturns(noteOrderManagementVo, id, principal))
//                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
//    }

    @PostMapping("confimSell")
    public ResponseEntity<ResponseDTO<Object>> confimSell(@RequestBody NoteOrderManagementVo noteOrderManagementVo,@RequestParam Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.confimSell(noteOrderManagementVo, id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }


    @GetMapping("getWarranty/{orderId}")
    public ResponseEntity<ResponseDTO<Object>> getWarranty(@PathVariable Integer orderId, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.getWarranty( orderId,  principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
    @PostMapping("updateNote")
    public ResponseEntity<ResponseDTO<Object>> updateNoteOrderManagement(@RequestBody NoteOrderManagementVo noteOrderManagementVo,@RequestParam Integer id, Principal principal) {
        return ResponseEntity.ok(ResponseDTO.builder().object(ordersService.updateNoteOrderManagement(noteOrderManagementVo, id, principal))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }
}
