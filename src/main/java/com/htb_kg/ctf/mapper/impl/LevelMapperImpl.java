package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.level.LevelResponse;
import com.htb_kg.ctf.entities.Level;
import com.htb_kg.ctf.mapper.LevelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LevelMapperImpl implements LevelMapper {
    @Override
    public List<LevelResponse> toDtoS(List<Level> all) {
        List<LevelResponse> levelResponses = new ArrayList<>();
        for (Level level: all){
            levelResponses.add(toDto(level));
        }
        return levelResponses;
    }

    @Override
    public LevelResponse toDto(Level level) {
        LevelResponse levelResponse = new LevelResponse();
        levelResponse.setName(level.getName());
        return levelResponse;
    }
}
