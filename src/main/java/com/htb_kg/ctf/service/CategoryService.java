package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.category.CategoryResponse;
import com.htb_kg.ctf.entities.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    Category findByName(String name);

    void saveCategory(String category);

    void deleteCategory(String category);

    void update(String categoryName, String setCategory);

    void deleteAll();
}
