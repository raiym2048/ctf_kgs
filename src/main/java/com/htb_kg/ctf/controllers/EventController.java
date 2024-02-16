package com.htb_kg.ctf.controllers;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyCreateRequest;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class EventController {
    private final EventService eventService;

    @PostMapping("/create")
    public String create(@RequestBody JeopardyCreateRequest createRequest, @RequestHeader("Authorization") String token){
        return eventService.create(createRequest, token);
    }

    @GetMapping("/all")
    public List<JeopardyResponse> getAll(){
        return eventService.getAll();
    }
    @GetMapping("/{eventId}/tasks")
    public List<TaskResponse> eventTasks(@PathVariable Long eventId, @RequestHeader("Authorization") String token){
        return eventService.eventTasks(eventId, token);
    }

    @GetMapping("/past")
    public List<JeopardyResponse> pastEvents(){
        return eventService.pastEvents();
    }

    @GetMapping("/ongoing")
    public List<JeopardyResponse> ongoing(){
        return eventService.ongoing();
    }

    @GetMapping("/upcoming")
    public List<JeopardyResponse> upcoming(){
        return eventService.upcoming();
    }

    @PostMapping("/join/{eventId}")
    public void joinToEvent(@PathVariable Long eventId, @RequestHeader("Authorization") String token){
        eventService.joinHacker(eventId, token);
    }

    @GetMapping("/joined")
    public List<JeopardyResponse> joinedEvents(@RequestHeader("Authorization") String token){
        return eventService.hackerJoinedEvents(token);
    }



}
