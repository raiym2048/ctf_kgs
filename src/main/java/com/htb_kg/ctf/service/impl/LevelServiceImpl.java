package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.level.LevelResponse;
import com.htb_kg.ctf.entities.Level;
import com.htb_kg.ctf.exception.NotFoundException;
import com.htb_kg.ctf.mapper.LevelMapper;
import com.htb_kg.ctf.repositories.LevelRepository;
import com.htb_kg.ctf.service.LevelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LevelServiceImpl implements LevelService {
    private final LevelMapper levelMapper;
    private final LevelRepository levelRepository;
    @Override
    public List<LevelResponse> getAll() {
        return levelMapper.toDtoS(levelRepository.findAll());
    }
    @Override
    public Level findByName(String name){
        Optional<Level> level = levelRepository.findByName(name);
        if (level.isEmpty())
            throw new NotFoundException("level with this name not found!", HttpStatus.BAD_REQUEST);
        return level.get();
    }

    @Override
    public void addLevel(String level) {
        if (levelRepository.findByName(level).isPresent())
            throw new NotFoundException("level with this name is already exist!", HttpStatus.BAD_GATEWAY);
        Level level1 = new Level();
        level1.setName(level);
        levelRepository.save(level1);
    }

    @Override
    public void deleteByName(String name) {
        if (levelRepository.findByName(name).isEmpty())
            throw new NotFoundException("level with this name not found!", HttpStatus.BAD_GATEWAY);
        levelRepository.deleteByName(name);
    }

    @Override
    public void update(String oldLevel, String newLevel) {
        Optional<Level> level = levelRepository.findByName(oldLevel);
        if (level.isEmpty())
            throw new NotFoundException("Level with this name not found!", HttpStatus.BAD_GATEWAY);
        level.get().setName(newLevel);
        levelRepository.save(level.get());
    }
}
