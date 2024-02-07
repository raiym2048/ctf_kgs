package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.hacker.HackerProfileResponse;
import com.htb_kg.ctf.dto.hacker.HackerResponse;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.mapper.HackerMapper;
import com.htb_kg.ctf.mapper.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HackerMapperImpl implements HackerMapper {
    private final TaskMapper taskMapper;
    @Override
    public HackerProfileResponse toProfileDto(Hacker hacker) {
        HackerProfileResponse response = new HackerProfileResponse();
        response.setId(hacker.getId());
        response.setEmail(hacker.getUser().getEmail());
        response.setNickname(hacker.getUser().getNickname());
        response.setPoints(hacker.getPoints());
        response.setFavorites(taskMapper.toDtoS(hacker.getFavorites(), hacker));
        response.setSolvedTaskCount(hacker.getAnsweredTasks().size());
        response.setSolvedTasks(taskMapper.toDtoS(hacker.getAnsweredTasks(), hacker));
        return response;
    }
}
