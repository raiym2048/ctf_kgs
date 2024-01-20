package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.category.CategoryResponse;
import com.htb_kg.ctf.entities.Category;
import com.htb_kg.ctf.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public List<CategoryResponse> toDtoS(List<Category> all) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category category: all){
            categoryResponses.add(toDto(category));
        }
        return categoryResponses;
    }

    @Override
    public CategoryResponse toDto(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }
}
