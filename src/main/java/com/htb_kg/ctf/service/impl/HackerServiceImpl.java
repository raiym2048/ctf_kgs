package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.HackerTaskAnswerHistory;
import com.htb_kg.ctf.entities.Task;
import com.htb_kg.ctf.entities.User;
import com.htb_kg.ctf.exception.NotFoundException;
import com.htb_kg.ctf.mapper.UserMapper;
import com.htb_kg.ctf.repositories.HackerRepository;
import com.htb_kg.ctf.repositories.TaskHackerHistoryRepository;
import com.htb_kg.ctf.repositories.TaskRepository;
import com.htb_kg.ctf.service.HackerService;
import com.htb_kg.ctf.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HackerServiceImpl implements HackerService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskHackerHistoryRepository historyRepository;
    private final HackerRepository hackerRepository;
    private final UserMapper userMapper;
    @Override
    public boolean answerToTask(String token, String answer, Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        User user = userService.getUsernameFromToken(token);
        if (task.isEmpty())
            throw new NotFoundException("The task with id "+ task+" is not exist!", HttpStatus.BAD_GATEWAY);

        if (!historyRepository.findHackerTaskAnswerHistoryByTaskIdAndHackerId(taskId, user.getHacker().getId()).isEmpty())
            throw new NotFoundException("hacker already answered to this task at: "+historyRepository.
                    findHackerTaskAnswerHistoryByTaskIdAndHackerId(
                            taskId, user.getHacker().getId()).
                                get().getTime()+
                    "!", HttpStatus.BAD_GATEWAY);
        if (task.get().getSubmitFlag().equals(answer)){
            HackerTaskAnswerHistory history = new HackerTaskAnswerHistory();
            List<Task> tasks = history.getTask()==null? new ArrayList<>() : history.getTask();
            tasks.add(task.get());
            history.setTask(tasks);
            List<Hacker> hackers = history.getHacker()==null? new ArrayList<>(): history.getHacker();
            hackers.add(user.getHacker());
            history.setHacker(hackers);
            history.setTime(LocalDateTime.now().toString());
            historyRepository.save(history);





            Hacker hacker = user.getHacker();
            List<HackerTaskAnswerHistory> histories = hacker.getHistory()==null? new ArrayList<>(): hacker.getHistory();
            histories.add(history);
            hacker.setHistory(histories);
            hacker.setPoints(hacker.getPoints()==null?task.get().getPoints(): hacker.getPoints()+ task.get().getPoints());
            List<Task> answeredTasks = new ArrayList<>();
            if (!hacker.getAnsweredTasks().isEmpty())
                answeredTasks = hacker.getAnsweredTasks();
            answeredTasks.add(task.get());
            hacker.setAnsweredTasks(answeredTasks);
            hackerRepository.save(hacker);
            return true;
        }

        return false;
    }

    @Override
    public List<RankingResponse> taskRanking() {
        return userMapper.toRanking(hackerRepository.findAllByOrderByPointsDesc());
    }
}
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUBnbWFpbC5jb20iLCJhdXRoIjp7ImF1dGhvcml0eSI6IkhBQ0tFUiJ9LCJpYXQiOjE3MDM3NTYwMDAsImV4cCI6MTcwNDA1NjAwMH0.68pkfCfCDPrLti0QgTxH8z-dJhblJSVUbf_gB7EZ1qU