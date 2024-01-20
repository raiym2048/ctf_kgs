package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.category.CategoryResponse;
import com.htb_kg.ctf.entities.Category;

import java.util.List;

public interface CategoryMapper {
    List<CategoryResponse> toDtoS(List<Category> all);

    CategoryResponse toDto(Category category);
}
