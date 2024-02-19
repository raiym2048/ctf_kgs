package com.htb_kg.ctf.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.entities.Event;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.Jeopardy;
import com.htb_kg.ctf.mapper.FileMapper;
import com.htb_kg.ctf.mapper.JeopardyMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JeopardyMapperImpl implements JeopardyMapper {
    private final FileMapper fileMapper;

    @Override
    public List<JeopardyResponse> toDtoS(List<Event> all) {
        List<JeopardyResponse> jeopardyResponses = new ArrayList<>();
        for (Event jeopardy: all)
            jeopardyResponses.add(toDto(jeopardy));
        return jeopardyResponses;
    }

    @Override
    public List<JeopardyResponse> toDtoS(List<Event> all, Hacker hacker) {
        List<JeopardyResponse> jeopardyResponses = new ArrayList<>();
        for (Event jeopardy: all)
            jeopardyResponses.add(toDto(jeopardy, hacker));
        return jeopardyResponses;
    }

    @Override
    public JeopardyResponse toDto(Event jeopardy) {
        List<Integer> startedDateArray = new ArrayList<>();
        startedDateArray.add(jeopardy.getStartDate().getYear());
        startedDateArray.add(jeopardy.getStartDate().getDayOfMonth());
        startedDateArray.add(jeopardy.getStartDate().getMonthValue());
        startedDateArray.add(jeopardy.getStartDate().getHour());
        startedDateArray.add(jeopardy.getStartDate().getMinute());

        List<Integer> endDateArray = new ArrayList<>();
        endDateArray.add(jeopardy.getEndDate().getYear());
        endDateArray.add(jeopardy.getEndDate().getDayOfMonth());
        endDateArray.add(jeopardy.getEndDate().getMonthValue());
        endDateArray.add(jeopardy.getEndDate().getHour());
        endDateArray.add(jeopardy.getEndDate().getMinute());

        JeopardyResponse jeopardyResponse = new JeopardyResponse();
        jeopardyResponse.setJeopardyId(jeopardy.getId());
        jeopardyResponse.setTitle(jeopardy.getTitle());
        jeopardyResponse.setStartDate(startedDateArray);
        jeopardyResponse.setEndDate(endDateArray);
        jeopardyResponse.setDescription(jeopardy.getDescription());
        jeopardyResponse.setEventStatus(jeopardy.getEventStatus()!=null?jeopardy.getEventStatus().getTitle():"");
        jeopardyResponse.setEventFormat(jeopardy.getEventFormat()!=null?jeopardy.getEventFormat().getTitle():"");
        jeopardyResponse.setEventType(jeopardy.getEventType()!=null?jeopardy.getEventType().getTitle():"");
        jeopardyResponse.setLocation(jeopardy.getLocation()!=null?jeopardy.getLocation().getTitle():"");
        jeopardyResponse.setChallengesCount(jeopardy.getChallenges()!=null?jeopardy.getChallenges().size() : 0);
        jeopardyResponse.setPlayersJoined(jeopardy.getJoinedHackers()!=null?jeopardy.getJoinedHackers().size():0);
        jeopardyResponse.setTeamJoined(jeopardy.getJoinedTeams()!=null?jeopardy.getJoinedTeams().size():0);

        if (jeopardy.getImage()!= null){
            jeopardyResponse.setEventImage(fileMapper.toDto(jeopardy.getImage()));
        }
        return jeopardyResponse;
    }

    @Override
    public JeopardyResponse toDto(Event jeopardy, Hacker hacker) {
        List<Integer> startedDateArray = new ArrayList<>();
        startedDateArray.add(jeopardy.getStartDate().getYear());
        startedDateArray.add(jeopardy.getStartDate().getDayOfMonth());
        startedDateArray.add(jeopardy.getStartDate().getMonthValue());
        startedDateArray.add(jeopardy.getStartDate().getHour());
        startedDateArray.add(jeopardy.getStartDate().getMinute());

        List<Integer> endDateArray = new ArrayList<>();
        endDateArray.add(jeopardy.getEndDate().getYear());
        endDateArray.add(jeopardy.getEndDate().getDayOfMonth());
        endDateArray.add(jeopardy.getEndDate().getMonthValue());
        endDateArray.add(jeopardy.getEndDate().getHour());
        endDateArray.add(jeopardy.getEndDate().getMinute());

        JeopardyResponse jeopardyResponse = new JeopardyResponse();
        jeopardyResponse.setJeopardyId(jeopardy.getId());
        jeopardyResponse.setDescription(jeopardy.getDescription());
        jeopardyResponse.setTitle(jeopardy.getTitle());
        jeopardyResponse.setStartDate(startedDateArray);
        jeopardyResponse.setEndDate(endDateArray);
        jeopardyResponse.setEventStatus(jeopardy.getEventStatus()!=null?jeopardy.getEventStatus().getTitle():"");
        jeopardyResponse.setEventFormat(jeopardy.getEventFormat()!=null?jeopardy.getEventFormat().getTitle():"");
        jeopardyResponse.setEventType(jeopardy.getEventType()!=null?jeopardy.getEventType().getTitle():"");
        jeopardyResponse.setLocation(jeopardy.getLocation()!=null?jeopardy.getLocation().getTitle():"");
        jeopardyResponse.setChallengesCount(jeopardy.getChallenges()!=null?jeopardy.getChallenges().size() : 0);
        jeopardyResponse.setPlayersJoined(jeopardy.getJoinedHackers()!=null?jeopardy.getJoinedHackers().size():0);
        jeopardyResponse.setTeamJoined(jeopardy.getJoinedTeams()!=null?jeopardy.getJoinedTeams().size():0);
        if (jeopardy.getJoinedHackers().contains(hacker)){
            jeopardyResponse.setJoined(true);
        }
        else {
            jeopardyResponse.setJoined(false);

        }

        if (jeopardy.getImage()!= null){
            jeopardyResponse.setEventImage(fileMapper.toDto(jeopardy.getImage()));
        }
        return jeopardyResponse;
    }
}
