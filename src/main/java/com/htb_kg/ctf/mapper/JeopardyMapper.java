package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.entities.Event;
import com.htb_kg.ctf.entities.EventScoreBoard;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.Jeopardy;

import java.util.List;

public interface JeopardyMapper {
    List<JeopardyResponse> toDtoS(List<Event> all);
    List<JeopardyResponse> toDtoS(List<Event> all, Hacker hacker);

    JeopardyResponse toDto(Event jeopardy);
    JeopardyResponse toDto(Event jeopardy, Hacker hacker);

    List<RankingResponse> toDtoSRanking(List<EventScoreBoard> allByIdOrderByPointAsc);

    RankingResponse toDtoRanking(EventScoreBoard eventScoreBoard);
}
