package com.htb_kg.ctf.dataLoader;

import com.htb_kg.ctf.entities.Category;
import com.htb_kg.ctf.entities.Level;
import com.htb_kg.ctf.entities.Task;
import com.htb_kg.ctf.repositories.CategoryRepository;
import com.htb_kg.ctf.repositories.LevelRepository;
import com.htb_kg.ctf.repositories.TaskRepository;
import com.htb_kg.ctf.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.findAll().size()< 5)
            for (int i = 0;i < 10;i++){
                Category category = new Category();
                category.setName("category "+i+"!");
                categoryRepository.save(category);
            }
        if (levelRepository.findAll().size()<5){
            for (int i = 0;i < 10;i++){
                Level level = new Level();
                level.setName("level "+i);
                levelRepository.save(level);
            }
        }
        if (taskRepository.findAll().size()<5){
            Random random = new Random();
            for (int i = 0;i < 10;i++){

                Task task = new Task();
                task.setTaskCreator("raiymbek");
                task.setPoints(random.nextInt(500 - 100 + 1) + 100);
                task.setName("simple task name");
                task.setUserSolves(0);
                task.setDescription("description");
                task.setReleaseDate(LocalDateTime.now().toString());
                task.setLevel(levelRepository.findById(Long.valueOf(i+1)).get());
                task.setCategory(categoryRepository.findById(Long.valueOf(i+1)).get());
                task.setSubmitFlag("simple flag");
                taskRepository.save(task);
            }
        }
    }
}
