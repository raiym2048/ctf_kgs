package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.level.LevelResponse;
import com.htb_kg.ctf.entities.Level;

import java.util.List;

public interface LevelService {
    List<LevelResponse> getAll();

    Level findByName(String name);

    void addLevel(String level);

    void deleteByName(String name);

    void update(String oldLevel, String newLevel);
}
