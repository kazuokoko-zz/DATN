package com.poly.datn.service;

import java.util.Optional;

public interface BlogService {
    Object getById(Integer id);

    Object getList(Optional<Integer> pid, Optional<String> title);
}
