package com.htb_kg.ctf.controllers;

import com.htb_kg.ctf.dto.event.EventScoreBoardResponse;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyCreateRequest;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.EventScoreBoard;
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

    @GetMapping("/{eventId}")
    public JeopardyResponse byId(@RequestHeader("Authorization") String token, @PathVariable Long eventId){
        return eventService.getById(token, eventId);
    }

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
    public List<JeopardyResponse> pastEvents(@RequestHeader("Authorization") String token){
        if (token.isEmpty()){
            return eventService.pastEvents();
        }
        return eventService.pastEvents(token);
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
        System.out.println("\n\n\ncalling     @PostMapping(\"/join/{eventId}\")\n");
        eventService.joinHacker(eventId, token);
    }

    @GetMapping("/joined")
    public List<JeopardyResponse> joinedEvents(@RequestHeader("Authorization") String token){
        return eventService.hackerJoinedEvents(token);
    }

    @GetMapping("/{eventId}/challenges/search")
    public List<TaskResponse> search(@RequestHeader("Authorization") String token, @PathVariable Long eventId, @RequestParam(required = false) String serach){
        return eventService.searchByEventChallenges(token, eventId, serach);
    }

    @GetMapping("/{eventId}/challenges/search/byCategory")
    public List<TaskResponse> seachByCategory(@RequestHeader("Authorization") String token,
                                              @PathVariable Long eventId, @RequestParam(required = false) String categoryName){
        return eventService.searchByCategoryInEventChallenges(token, eventId, categoryName);
    }

    @GetMapping("/scoreboard/{eventId}")
    public EventScoreBoardResponse eventRanking(@PathVariable Long eventId){
        return eventService.rankingById(eventId);
    }
}
