package com.htb_kg.ctf.controllers;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyCreateRequest;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.service.JeopardyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class JeopardyController {
    private final JeopardyService jeopardyService;

    @PostMapping("/create")
    public String create(@RequestBody JeopardyCreateRequest createRequest, @RequestHeader("Authorization") String token){
        return jeopardyService.create(createRequest, token);
    }

    @GetMapping("/all")
    public List<JeopardyResponse> getAll(){
        return jeopardyService.getAll();
    }
    @GetMapping("/{eventId}/tasks")
    public List<TaskResponse> eventTasks(@PathVariable Long eventId, @RequestHeader("Authorization") String token){
        return jeopardyService.eventTasks(eventId, token);
    }

}
