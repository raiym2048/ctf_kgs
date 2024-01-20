package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.task.TaskRequest;
import com.htb_kg.ctf.dto.task.TaskResponse;

import java.util.List;

public interface TaskService {
    void addTask(TaskRequest taskRequest, String token);

    void updateTask(TaskRequest taskRequest, Long taskId);

    List<TaskResponse> getAll(String token);

    void deleteById(Long id);
}
