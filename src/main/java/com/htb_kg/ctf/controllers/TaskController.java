package com.htb_kg.ctf.controllers;


import com.htb_kg.ctf.dto.task.*;
import com.htb_kg.ctf.entities.Task;
import com.htb_kg.ctf.repositories.TaskRepository;
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
    private final TaskRepository taskRepository;

    @GetMapping("/search")
    public List<TaskResponse> taskResponses2(@RequestParam(required = false) String searchRequest, @RequestHeader("Authorization") String token){
        return taskService.search(searchRequest, token);
    }

    @GetMapping("/filter")
    public List<TaskResponse> taskResponses(@RequestParam(required = false) Boolean s1,
                                            @RequestParam(required = false) Boolean s2,
                                            @RequestParam(required = false) Boolean s3, @RequestHeader("Authorization") String token){
        return taskService.filter(s1, s2,s3, token);
    }
    @GetMapping("/search/by/category")
    public List<TaskResponse> taskResponses1(@RequestParam(required = false) String request, @RequestHeader("Authorization") String token){
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

    @GetMapping("/getAllTasksAnonymous")
    public List<Task> tasks(){
        return taskRepository.findAll();
    }


    @GetMapping("/private/tasks")
    public List<TaskResponse> privateTasks(@RequestHeader("Authorization") String token){
        return taskService.privateTasks(token);
    }

    @PostMapping("/set/public")
    public void setPublic(@RequestBody ListId listId, @RequestHeader("Authorization") String token){
        taskService.setTaskPublic(listId, token);
    }

    @PostMapping("/set/private")
    public void setPrivate(@RequestBody ListId listId, @RequestHeader("Authorization") String token){
        taskService.setTaskPrivate(listId, token);
    }



}
