package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.event.JoinEvent;
import com.htb_kg.ctf.dto.hacker.HackerUpdateRequest;
import com.htb_kg.ctf.dto.rank.RankingResponse;

import java.util.List;

public interface HackerService {
    boolean answerToTask(String token, String answer, Long taskId);

    List<RankingResponse> taskRanking();

    void joinToEvent(JoinEvent joinEvent, String token);

    void update(HackerUpdateRequest request, String token);
}
