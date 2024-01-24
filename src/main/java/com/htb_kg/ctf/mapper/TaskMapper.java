package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.task.TaskRequest;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.HackerTaskAnswerHistory;
import com.htb_kg.ctf.entities.Task;

import java.util.List;

public interface TaskMapper {
    List<TaskResponse> toDtoS(List<Task> all, List<Task> answeredTasks);

    TaskResponse toDto(Task task, Boolean b);

    List<TaskResponse> toDtoS(List<Task> all);

    TaskResponse toDto(Task task);
}
