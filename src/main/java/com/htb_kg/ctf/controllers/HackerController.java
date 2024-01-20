package com.htb_kg.ctf.controllers;

import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.dto.user.UserResponse;
import com.htb_kg.ctf.service.HackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hacker")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class HackerController {
    private final HackerService hackerService;

    @PostMapping("/answer_task/{taskId}")
    public boolean answerToTheTask(@RequestHeader("Authorization") String token,
                                   @RequestParam(required = false) String answer,
                                   @PathVariable Long taskId){
        return hackerService.answerToTask(token, answer, taskId);
    }

    @GetMapping("/ranking")
    public List<RankingResponse> ranking(){
        return hackerService.taskRanking();
    }
}
