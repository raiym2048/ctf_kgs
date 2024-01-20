package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByName(String name);
    void deleteByName(String name);
}
