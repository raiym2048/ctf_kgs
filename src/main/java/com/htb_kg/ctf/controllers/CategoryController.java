package com.htb_kg.ctf.controllers;

import com.htb_kg.ctf.dto.category.CategoryResponse;
import com.htb_kg.ctf.entities.Category;
import com.htb_kg.ctf.service.CategoryService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.util.List;


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/allCategories")
    public List<CategoryResponse> categories(){
        return categoryService.getAllCategories();
    }
    @PostMapping("/addCategory")
    public void addCategory(@RequestParam(required = false) String category){
        categoryService.saveCategory(category);
    }
    @DeleteMapping("/deleteCategory")
    public void deleteCategory(@RequestParam(required = false) String category){
        categoryService.deleteCategory(category);
    }
    @PostMapping("/update")
    public void updateCategory(@RequestParam(required = false) String categoryName, @RequestParam(required = false) String setCategory){
        categoryService.update(categoryName, setCategory);
    }
    @DeleteMapping("/deleteAllCategory")
    public void deleteAll(){
        categoryService.deleteAll();
    }
}
