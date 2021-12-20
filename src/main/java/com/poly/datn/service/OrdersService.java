package com.poly.datn.service;

import com.poly.datn.vo.NewOrdersVO;
import com.poly.datn.vo.VoBoSung.OrderDTO.OrdersVO;
import com.poly.datn.vo.VoBoSung.NoteOrderManagementVo;
import com.poly.datn.vo.VoBoSung.ShowProductWarrantyVO;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface OrdersService {
    List<OrdersVO> getByUsername(Principal principal);

    List<OrdersVO> getAll(Principal principal);

    OrdersVO getByIdAndUserName(Integer id, Principal principal) throws SecurityException;
//, OrderDetailsVO orderDetailsVO, CustomerVO customerVO
    OrdersVO newOrder(NewOrdersVO ordersVO, Principal principal);

    OrdersVO getByIdAndUserNameAdmin(Integer id, Principal principal);

    boolean cancerOrder(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);

    boolean confimOrder(NoteOrderManagementVo noteOrderManagementVo ,Integer id, Principal principal);

    boolean confimTransport(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);
    boolean requestReturns(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);
    boolean comfimReturns(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);
    boolean unCancerOrder(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);
    boolean confimSell(NoteOrderManagementVo noteOrderManagementVo ,Integer id, Principal principal);
    boolean updateNoteOrderManagement(NoteOrderManagementVo noteOrderManagementVo ,Integer id, Principal principal);

    ShowProductWarrantyVO getWarranty(Integer orderId, Principal principal);

    OrdersVO newOrderAdmin(NewOrdersVO ordersVO, Principal principal);

    OrdersVO updateOrderAdmin(Optional<Integer> id, Optional<String> status, Principal principal);

    List<OrdersVO> getList(Principal principal, Optional<Integer> id, Optional<String> email, Optional<String> name, Optional<String> phone);
    boolean cancerOrderUser(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);
    boolean requestCancerOrderUser(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);
    boolean unCancerOrderUser(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);
     boolean confimReturnsUser(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);
     boolean unConfimReturnsUser(NoteOrderManagementVo noteOrderManagementVo, Integer id, Principal principal);
}
