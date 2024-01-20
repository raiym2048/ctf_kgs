package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.task.TaskRequest;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.Category;
import com.htb_kg.ctf.entities.HackerTaskAnswerHistory;
import com.htb_kg.ctf.entities.Task;
import com.htb_kg.ctf.entities.User;
import com.htb_kg.ctf.exception.NotFoundException;
import com.htb_kg.ctf.mapper.TaskMapper;
import com.htb_kg.ctf.repositories.CategoryRepository;
import com.htb_kg.ctf.repositories.FileRepository;
import com.htb_kg.ctf.repositories.LevelRepository;
import com.htb_kg.ctf.repositories.TaskRepository;
import com.htb_kg.ctf.service.CategoryService;
import com.htb_kg.ctf.service.LevelService;
import com.htb_kg.ctf.service.TaskService;
import com.htb_kg.ctf.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final LevelService levelService;
    private final FileRepository fileRepository;
    private final LevelRepository levelRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    @Override
    public void addTask(TaskRequest taskRequest, String token) {

        taskRepository.save(requestToEntity(taskRequest));

    }

    @Override
    @Transactional
    public void updateTask(TaskRequest taskRequest, Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty())
            throw new NotFoundException("task with id "+taskId+" is not found!", HttpStatus.BAD_GATEWAY);
        taskRepository.updateTaskAttributes(taskId,
                taskRequest.getName(), taskRequest.getDescription(), taskRequest.getPoints(),taskRequest.getLevelName().isEmpty()?null: levelRepository.findByName(taskRequest.getLevelName()).get(),
                taskRequest.getUserSolves(),taskRequest.getCategoryName().isEmpty()?null: categoryRepository.findCategoryByName(taskRequest.getCategoryName()).get(),
                taskRequest.getReleaseDate(), taskRequest.getTaskCreator(),
                taskRequest.getSubmitFlag());
    }

    @Override
    public List<TaskResponse> getAll(String token) {
        User user = userService.getUsernameFromToken(token);
        List<Task> answeredTasks = user.getHacker().getAnsweredTasks();
        System.out.println("the size:"+answeredTasks.size());

        return taskMapper.toDtoS(taskRepository.findAll(), answeredTasks);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty())
            throw new NotFoundException("Task with id "+id+" is not exist!", HttpStatus.BAD_GATEWAY);
        taskRepository.deleteById(id);
    }

    private Task requestToEntity(TaskRequest taskRequest) {
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setTaskCreator(taskRequest.getTaskCreator());
        task.setPoints(taskRequest.getPoints());
        task.setDescription(taskRequest.getDescription());
        task.setReleaseDate(taskRequest.getReleaseDate());
        task.setSubmitFlag(taskRequest.getSubmitFlag());
        task.setUserSolves(taskRequest.getUserSolves());
        task.setLevel(!taskRequest.getLevelName().isBlank()? levelService.findByName(taskRequest.getLevelName()):null);
        task.setCategory(!taskRequest.getCategoryName().isEmpty()? categoryService.findByName(taskRequest.getCategoryName()):null);


        return task;
    }
}
