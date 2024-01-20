package com.htb_kg.ctf.controllers;


import com.htb_kg.ctf.dto.task.TaskIdRequest;
import com.htb_kg.ctf.dto.task.TaskRequest;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/addTask")
    public void addTask(@RequestBody TaskRequest taskRequest, @RequestHeader("Authorization") String token){

        taskService.addTask(taskRequest, token);
    }

    @PostMapping("/update/")
    public void updateTask(@RequestBody TaskRequest taskRequest){
        taskService.updateTask(taskRequest, taskRequest.getOldTaskId());
    }

    @GetMapping("/all")
    public List<TaskResponse> allTasks(@RequestHeader("Authorization") String token){
        return taskService.getAll(token);
    }

    @DeleteMapping("/deleteById")
    public void deleteById(@RequestBody TaskIdRequest idRequest){
        taskService.deleteById(idRequest.getId());
    }



}
