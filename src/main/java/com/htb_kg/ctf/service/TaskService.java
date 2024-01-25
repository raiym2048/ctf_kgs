package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.category.CategoryResponse;
import com.htb_kg.ctf.dto.task.FilterRequest;
import com.htb_kg.ctf.dto.task.TaskRequest;
import com.htb_kg.ctf.dto.task.TaskResponse;

import java.util.List;

public interface TaskService {
    void addTask(TaskRequest taskRequest, String token);

    void updateTask(TaskRequest taskRequest, Long taskId);

    List<TaskResponse> getAll(String token);

    void deleteById(Long id);

    List<TaskResponse> filter(FilterRequest category, String token);

    void favorite(Long taskId, String token);

    TaskResponse getById(Long taskId, String token);

    void likeTask(Long taskId, String token, Boolean like);

    String openHint(Long id, String token);
}
