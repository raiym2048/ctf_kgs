package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.category.CategoryResponse;
import com.htb_kg.ctf.dto.task.*;

import java.util.List;

public interface TaskService {
    void addTask(TaskRequest taskRequest, String token);

    void updateTask(TaskRequest taskRequest, Long taskId);

    List<TaskResponse> getAll(String token);

    void deleteById(Long id);

    List<TaskResponse> filter(FilterRequest category, String token);

    Boolean favorite(Long taskId, String token);

    TaskResponse getById(Long taskId, String token);

    LikeResponse likeTask(Long taskId, String token);
    LikeResponse disLikeTask(Long taskId, String token);

    String openHint(Long id, String token);

    List<TaskResponse> byCategory(TaskCategoryNameSearchRequest request, String token);

    List<TaskResponse> search(SearchRequest searchRequest, String token);
}
