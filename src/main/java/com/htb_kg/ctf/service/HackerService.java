package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.rank.RankingResponse;

import java.util.List;

public interface HackerService {
    boolean answerToTask(String token, String answer, Long taskId);

    List<RankingResponse> taskRanking();
}
