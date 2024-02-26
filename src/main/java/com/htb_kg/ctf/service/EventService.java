package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.event.EventScoreBoardResponse;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyCreateRequest;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.Event;
import com.htb_kg.ctf.entities.Hacker;

import java.util.List;

public interface EventService {
    String create(JeopardyCreateRequest createRequest, String token);

    List<JeopardyResponse> getAll();

    List<TaskResponse> eventTasks(Long eventId, String token);

    Integer pastCount(String token);

    List<JeopardyResponse> pastEvents();
    List<JeopardyResponse> pastEvents(String token);

    List<JeopardyResponse> ongoing();

    List<JeopardyResponse> upcoming();

    List<JeopardyResponse> hackerPastEvents(String token);

    void joinHacker(Long eventId, String token);

    void eventScoreBoardSave(Event event, Hacker hacker);

    List<JeopardyResponse> hackerJoinedEvents(String token);

    JeopardyResponse getById(String token, Long eventId);

    List<TaskResponse> searchByEventChallenges(String token, Long eventId, String seach);

    List<TaskResponse> searchByCategoryInEventChallenges(String token, Long eventId, String categoryName);

    EventScoreBoardResponse rankingById(Long eventId);
}
