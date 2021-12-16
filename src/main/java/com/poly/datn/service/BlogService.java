package com.poly.datn.service;

import com.poly.datn.vo.BlogVO;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface BlogService {
    Object getById(Integer id);
    Object getOneByIdAdmin(Integer id, Principal principal);

    Object getList(Optional<Integer> pid, Optional<String> title);

    BlogVO getABlog(Integer id, Principal principal);

    List<BlogVO> getListAdmin(Optional<Integer> pid, Optional<String> title, Principal principal);
    List<BlogVO> getListDeleteAdmin(Optional<Integer> pid, Optional<String> title, Principal principal);

    Boolean deleteById(Optional<Integer> id, Principal principal);

    BlogVO create(BlogVO blogVO, Principal principal);

    BlogVO update(BlogVO blogVO, Optional<Integer> id, Principal principal) throws ParseException;
}
