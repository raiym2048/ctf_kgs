package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.category.CategoryResponse;
import com.htb_kg.ctf.entities.Category;
import com.htb_kg.ctf.exception.NotFoundException;
import com.htb_kg.ctf.mapper.CategoryMapper;
import com.htb_kg.ctf.repositories.CategoryRepository;
import com.htb_kg.ctf.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toDtoS(categoryRepository.findAll());
    }

    @Override
    public Category findByName(String name){
        Optional<Category> category = categoryRepository.findCategoryByName(name);
        if (category.isEmpty())
            throw new NotFoundException("category with name "+name+" not found!", HttpStatus.BAD_GATEWAY);
        return category.get();
    }

    @Override
    public void saveCategory(String category) {
        if(categoryRepository.findCategoryByName(category).isPresent())
            throw new NotFoundException("the category already exists!", HttpStatus.BAD_REQUEST);
        Category category1 = new Category();
        category1.setName(category);
        categoryRepository.save(category1);
    }

    @Override
    public void deleteCategory(String category) {
        if (categoryRepository.findCategoryByName(category).isEmpty())
            throw new NotFoundException("category not found!", HttpStatus.BAD_GATEWAY);
        categoryRepository.deleteByName(category);
    }

    @Override
    public void update(String categoryName, String inCategory) {
        Optional<Category> category = categoryRepository.findCategoryByName(categoryName);
        if (category.isEmpty())
            throw new NotFoundException("category not found!", HttpStatus.BAD_GATEWAY);
        category.get().setName(inCategory);
        categoryRepository.save(category.get());
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
