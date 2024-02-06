package com.htb_kg.ctf.controllers;


import com.htb_kg.ctf.dto.category.CategoryResponse;
import com.htb_kg.ctf.dto.task.*;
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

    @GetMapping("/filter")
    public List<TaskResponse> taskResponses(@RequestBody FilterRequest filterRequest, @RequestHeader("Authorization") String token){
        return taskService.filter(filterRequest, token);
    }
    @GetMapping("/search/by/category")
    public List<TaskResponse> taskResponses(@RequestBody TaskCategoryNameSearchRequest request, @RequestHeader("Authorization") String token){
        return taskService.byCategory(request, token);
    }

    @PostMapping("/favorite/{taskId}")
    public Boolean saved(@PathVariable Long taskId, @RequestHeader("Authorization") String token){
        return taskService.favorite(taskId, token);
    }

    @GetMapping("/{taskId}")
    public TaskResponse taskResponse(@PathVariable Long taskId, @RequestHeader("Authorization") String token){
        return taskService.getById(taskId, token);
    }

    @PostMapping("/like/{taskId}")
    public LikeResponse likeTheTask(@PathVariable Long taskId, @RequestHeader("Authorization") String token){
        return taskService.likeTask(taskId, token);
    }

    @PostMapping("/disLike/{taskId}")
    public LikeResponse disLikeTheTask(@PathVariable Long taskId, @RequestHeader("Authorization") String token, Boolean like){
        return taskService.disLikeTask(taskId, token);
    }


    @PostMapping("/addTask")
    public void addTask(@RequestBody TaskRequest taskRequest, @RequestHeader("Authorization") String token){

        taskService.addTask(taskRequest, token);
    }

    @PostMapping("/open/hint/{id}")
    public String openHint(@PathVariable Long id, @RequestHeader("Authorization") String token){
        return taskService.openHint(id, token);
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
