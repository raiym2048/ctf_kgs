package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.level.LevelResponse;
import com.htb_kg.ctf.entities.Level;

import java.util.List;

public interface LevelMapper {
    List<LevelResponse> toDtoS(List<Level> all);

    LevelResponse toDto(Level level);
}
