package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyCreateRequest;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;

import java.util.List;

public interface EventService {
    String create(JeopardyCreateRequest createRequest, String token);

    List<JeopardyResponse> getAll();

    List<TaskResponse> eventTasks(Long eventId, String token);

    Integer pastCount(String token);

    List<JeopardyResponse> pastEvents();

    List<JeopardyResponse> ongoing();

    List<JeopardyResponse> upcoming();
}
