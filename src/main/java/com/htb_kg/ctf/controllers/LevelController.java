package com.htb_kg.ctf.controllers;


import com.htb_kg.ctf.dto.level.LevelNameRequest;
import com.htb_kg.ctf.dto.level.LevelResponse;
import com.htb_kg.ctf.dto.level.LevelUpdateRequest;
import com.htb_kg.ctf.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/level")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LevelController {
    private final LevelService levelService;

    @GetMapping("/getAllLevels")
    public List<LevelResponse> getAllLevels(){
        return levelService.getAll();
    }

    @PostMapping("/addLevel")
    public void addLevel(@RequestBody LevelNameRequest nameRequest){
        levelService.addLevel(nameRequest.getLevel());
    }
    @DeleteMapping("/deleteLevel")
    public void deleteLevel(@RequestBody LevelNameRequest nameRequest){
        levelService.deleteByName(nameRequest.getLevel());
    }
    @PostMapping("/update/Level")
    public void updateLevel(@RequestBody LevelUpdateRequest updateRequest){
        levelService.update(updateRequest.getOldLevel(), updateRequest.getNewLevel());
    }
}
