package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.entities.Event;
import com.htb_kg.ctf.entities.Jeopardy;
import com.htb_kg.ctf.mapper.JeopardyMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JeopardyMapperImpl implements JeopardyMapper {
    @Override
    public List<JeopardyResponse> toDtoS(List<Event> all) {
        List<JeopardyResponse> jeopardyResponses = new ArrayList<>();
        for (Event jeopardy: all)
            jeopardyResponses.add(toDto(jeopardy));
        return jeopardyResponses;
    }

    @Override
    public JeopardyResponse toDto(Event jeopardy) {
        JeopardyResponse jeopardyResponse = new JeopardyResponse();
        jeopardyResponse.setJeopardyId(jeopardy.getId());
        jeopardyResponse.setTitle(jeopardy.getTitle());
        jeopardyResponse.setStartDate(jeopardy.getStartDate());
        jeopardyResponse.setEndDate(jeopardy.getEndDate());
        jeopardyResponse.setEventStatus(jeopardy.getEventStatus()!=null?jeopardy.getEventStatus().getTitle():"");
        jeopardyResponse.setEventFormat(jeopardy.getEventFormat()!=null?jeopardy.getEventFormat().getTitle():"");
        jeopardyResponse.setEventType(jeopardy.getEventType()!=null?jeopardy.getEventType().getTitle():"");
        jeopardyResponse.setLocation(jeopardy.getLocation()!=null?jeopardy.getLocation().getTitle():"");
        jeopardyResponse.setChallengesCount(jeopardy.getChallenges()!=null?jeopardy.getChallenges().size() : 0);
        jeopardyResponse.setPlayersJoined(jeopardy.getJoinedHackers()!=null?jeopardy.getJoinedHackers().size():0);
        jeopardyResponse.setTeamJoined(jeopardy.getJoinedTeams()!=null?jeopardy.getJoinedTeams().size():0);
        return jeopardyResponse;
    }
}
