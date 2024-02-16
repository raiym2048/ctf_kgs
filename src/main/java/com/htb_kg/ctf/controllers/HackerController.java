package com.htb_kg.ctf.controllers;

import com.htb_kg.ctf.dto.event.JoinEvent;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.hacker.HackerAnswerTaskRequest;
import com.htb_kg.ctf.dto.hacker.HackerProfileResponse;
import com.htb_kg.ctf.dto.hacker.HackerResponse;
import com.htb_kg.ctf.dto.hacker.HackerUpdateRequest;
import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.dto.user.UserResponse;
import com.htb_kg.ctf.service.EventService;
import com.htb_kg.ctf.service.HackerService;
import com.htb_kg.ctf.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hacker")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class HackerController {
    private final HackerService hackerService;
    private final TaskService taskService;
    private final EventService eventService;

    @GetMapping("/byId")
    public HackerProfileResponse hackerResponse(@RequestHeader("Authorization") String token){
        return hackerService.getById(token);
    }

    @PostMapping("/answer_task/{taskId}")
    public boolean answerToTheTask(@RequestHeader("Authorization") String token,
                                   @RequestBody HackerAnswerTaskRequest answer){
        return hackerService.answerToTask(token, answer.getAnswer(), answer.getTaskId());
    }
    @PostMapping("/join/event")
    public void joinToEvent(@RequestBody JoinEvent joinEvent, @RequestHeader("Authorization") String token){
        hackerService.joinToEvent(joinEvent, token);
    }

    @GetMapping("/ranking")
    public List<RankingResponse> ranking(){
        return hackerService.taskRanking();
    }

    @PutMapping("/update")
    public void updateHacker(@RequestBody HackerUpdateRequest request, @RequestHeader("Authorization") String token){
        hackerService.update(request, token);
    }

    @GetMapping("/task/submitted/count")
    public Integer submittedCountTask(@RequestHeader("Authorization") String token){
        return taskService.hackerSubmittedTaskCount(token);
    }
    @GetMapping("/task/submitted")
    public List<TaskResponse> submittedTasks(@RequestHeader("Authorization") String token){
        return taskService.hackerSubmittedTasks(token);
    }

    @GetMapping("/events/past")
    public List<JeopardyResponse> pastEvents(@RequestHeader("Authorization") String token){
        return eventService.hackerPastEvents(token);
    }




}
