package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.task.*;

import java.util.List;

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

    List<TaskResponse> privateTasks(String token);

    void setTaskPublic(ListId listId, String token);

    void setTaskPrivate(ListId listId, String token);


    Integer hackerSubmittedTaskCount(String token);

    List<TaskResponse> hackerSubmittedTasks(String token);
}
