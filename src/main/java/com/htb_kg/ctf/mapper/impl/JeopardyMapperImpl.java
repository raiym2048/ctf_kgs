package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.entities.Event;
import com.htb_kg.ctf.entities.EventScoreBoard;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.mapper.FileMapper;
import com.htb_kg.ctf.mapper.JeopardyMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        List<Object> startDateArray = new ArrayList<>();
        startDateArray.add(jeopardy.getStartDate().getYear());
        startDateArray.add(jeopardy.getStartDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        startDateArray.add(jeopardy.getStartDate().getDayOfMonth());
        startDateArray.add(jeopardy.getStartDate().getHour());
        if (jeopardy.getStartDate().getMinute()< 9){
            startDateArray.add("0"+jeopardy.getStartDate().getMinute());
        }
        else {
            startDateArray.add(jeopardy.getStartDate().getMinute());

        }
        List<Object> endDateArray = new ArrayList<>();
        endDateArray.add(jeopardy.getEndDate().getYear());
        endDateArray.add(jeopardy.getEndDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        endDateArray.add(jeopardy.getEndDate().getDayOfMonth());
        endDateArray.add(jeopardy.getEndDate().getHour());
        if (jeopardy.getEndDate().getMinute()< 9){
            System.out.println("its true");
            endDateArray.add("0"+jeopardy.getEndDate().getMinute());
            System.out.println("0" + jeopardy.getEndDate().getMinute());
        }
        else {
            System.out.println("yts false");
            endDateArray.add(jeopardy.getEndDate().getMinute());
        }
        JeopardyResponse jeopardyResponse = new JeopardyResponse();
        jeopardyResponse.setJeopardyId(jeopardy.getId());
        jeopardyResponse.setTitle(jeopardy.getTitle());
        jeopardyResponse.setStartDate(startDateArray);
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
        List<Object> startDateArray = new ArrayList<>();
        startDateArray.add(jeopardy.getStartDate().getYear());
        startDateArray.add(jeopardy.getStartDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        startDateArray.add(jeopardy.getStartDate().getDayOfMonth());
        startDateArray.add(jeopardy.getStartDate().getHour());
        if (jeopardy.getStartDate().getMinute()< 9){
            startDateArray.add("0"+jeopardy.getStartDate().getMinute());
        }
        else {
            startDateArray.add(jeopardy.getStartDate().getMinute());

        }
        List<Object> endDateArray = new ArrayList<>();
        endDateArray.add(jeopardy.getEndDate().getYear());
        endDateArray.add(jeopardy.getEndDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        endDateArray.add(jeopardy.getEndDate().getDayOfMonth());
        endDateArray.add(jeopardy.getEndDate().getHour());
        if (jeopardy.getEndDate().getMinute()< 9){
            System.out.println("its true");
            endDateArray.add("0"+jeopardy.getEndDate().getMinute());
            System.out.println("0" + jeopardy.getEndDate().getMinute());
        }
        else {
            System.out.println("yts false");
            endDateArray.add(jeopardy.getEndDate().getMinute());
        }


        JeopardyResponse jeopardyResponse = new JeopardyResponse();
        jeopardyResponse.setJeopardyId(jeopardy.getId());
        jeopardyResponse.setDescription(jeopardy.getDescription());
        jeopardyResponse.setTitle(jeopardy.getTitle());
        jeopardyResponse.setStartDate(startDateArray);
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

    @Override
    public List<RankingResponse> toDtoSRanking(List<EventScoreBoard> allByIdOrderByPointAsc) {
        List<RankingResponse> responses = new ArrayList<>();
        for (EventScoreBoard eventScoreBoard: allByIdOrderByPointAsc){
            responses.add(toDtoRanking(eventScoreBoard));
        }
        return responses;
    }

    @Override
    public RankingResponse toDtoRanking(EventScoreBoard eventScoreBoard) {
        RankingResponse response = new RankingResponse();
        response.setPoints(eventScoreBoard.getPoint());
        response.setNickname(eventScoreBoard.getHacker().getUser().getNickname());
        if (eventScoreBoard.getHacker().getUser().getLogo_image()!=null){
            response.setLogo_image(fileMapper.toDto(eventScoreBoard.getHacker().getUser().getLogo_image()));
        }
        return response;
    }
}
