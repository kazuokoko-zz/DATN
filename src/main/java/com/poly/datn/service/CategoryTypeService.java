package com.poly.datn.service;


import com.poly.datn.entity.CategoryType;
import com.poly.datn.repository.CategoryTypeRepository;
import com.poly.datn.vo.CategoryTypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryTypeService implements ICategoryTypeService{


 @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    @Override
    public List<CategoryTypeVO> realAll() {
        List<CategoryTypeVO> voCategoryType = new ArrayList<CategoryTypeVO>();
        List<CategoryType> entities = categoryTypeRepository.findAll();
        for(CategoryType entity : entities){
            CategoryTypeVO vo = new CategoryTypeVO();
            BeanUtils.copyProperties(entity, vo);
            voCategoryType.add(vo);

        }
        return voCategoryType;
    }

    @Override
    public CategoryTypeVO create(CategoryTypeVO voCategoryType) {
        CategoryType entity = new CategoryType();
        BeanUtils.copyProperties(voCategoryType, entity);
         entity = categoryTypeRepository.save(entity);
        voCategoryType.setId(entity.getId());
        return voCategoryType;
    }
    @Override
    public CategoryTypeVO update(CategoryTypeVO voCategoryType) {
        Optional<CategoryType> optionalCategory_type = categoryTypeRepository.findById(voCategoryType.getId());
        if(optionalCategory_type.isPresent()){
            CategoryType entity = optionalCategory_type.get();
            BeanUtils.copyProperties(voCategoryType, entity);
            categoryTypeRepository.save(entity);
        }
        return voCategoryType;
    }

    @Override
    public CategoryTypeVO delete(Integer id) {
        CategoryTypeVO voCategoryType = new CategoryTypeVO();
        Optional<CategoryType> optionalCategory_type = categoryTypeRepository.findById(id);
        if(optionalCategory_type.isPresent()){
            CategoryType entity = optionalCategory_type.get();
            BeanUtils.copyProperties(entity,voCategoryType );
            categoryTypeRepository.delete(  entity);
        }
        return voCategoryType;
    }

}
