package com.poly.datn.service;

import com.poly.datn.vo.CategoryTypeVO;

import java.util.List;

public interface ICategoryTypeService {
    public List<CategoryTypeVO> realAll();

    public CategoryTypeVO create(CategoryTypeVO voCategoryType);

    public CategoryTypeVO update(CategoryTypeVO voCategoryType);

    public CategoryTypeVO delete(Integer id);
}
