package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.category.CategoryResponse;
import com.htb_kg.ctf.dto.hint.HintTexts;
import com.htb_kg.ctf.dto.task.*;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.Hint;
import com.htb_kg.ctf.entities.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    void addTask(TaskRequest taskRequest, String token);

    void updateTask(TaskRequest taskRequest, Long taskId);

    List<TaskResponse> getAll(String token);

    void deleteById(Long id);

    List<TaskResponse> filter(Boolean s1,Boolean s2, Boolean s3,  String token);

    Boolean favorite(Long taskId, String token);

    TaskResponse getById(Long taskId, String token);



    LikeResponse likeTask(Long taskId, String token);
    LikeResponse disLikeTask(Long taskId, String token);

    String openHint(Long id, String token);

    List<TaskResponse> byCategory(String request, String token);

    List<TaskResponse> search(String searchRequest, String token);

    List<TaskResponse> getAllEventTasks(Long eventId);
}
