package com.htb_kg.ctf.dto.hacker;

import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HackerProfileResponse {
    private Long id;
    private String email;
    private String nickname;
    private Integer points;
    private Integer solvedTaskCount;
    private List<TaskResponse> favorites;
    private List<TaskResponse> solvedTasks;
}
