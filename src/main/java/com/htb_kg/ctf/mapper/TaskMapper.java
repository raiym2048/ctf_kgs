package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.hint.HintTexts;
import com.htb_kg.ctf.dto.task.HintResponse;
import com.htb_kg.ctf.dto.task.LikeResponse;
import com.htb_kg.ctf.dto.task.TaskRequest;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.*;

import java.util.List;

public interface TaskMapper {
    List<TaskResponse> toDtoS(List<Task> all, List<Task> answeredTasks, Hacker hacker);

    TaskResponse toDto(Task task, Boolean b, LikeResponse likeResponse, Boolean b2, Hacker hacker);

    List<TaskResponse> toDtoS(List<Task> all, Hacker hacker);

    List<TaskResponse> toDtoS(List<Task> all);

    TaskResponse toDto(Task task, LikeResponse likeResponse, Hacker hacker);

    TaskResponse toDto(Task task);

    List<HintResponse> compareAndGetNotOpenedHints(List<Hint> taskHints, List<Hint> openedHints);

    List<HintResponse> getHackerHints(Hacker hacker, Task task);

    List<HintTexts> getHintText(Hacker hacker, Task task);

    List<TaskResponse> toDtoSEventtasks(Event event, Hacker hacker);
}
