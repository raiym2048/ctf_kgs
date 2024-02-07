package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.event.JoinEvent;
import com.htb_kg.ctf.dto.hacker.HackerUpdateRequest;
import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.entities.*;
import com.htb_kg.ctf.enums.Role;
import com.htb_kg.ctf.exception.BadRequestException;
import com.htb_kg.ctf.exception.NotFoundException;
import com.htb_kg.ctf.mapper.UserMapper;
import com.htb_kg.ctf.repositories.HackerRepository;
import com.htb_kg.ctf.repositories.TaskHackerHistoryRepository;
import com.htb_kg.ctf.repositories.TaskRepository;
import com.htb_kg.ctf.repositories.event.EventRepository;
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
    private final EventRepository eventRepository;
    @Override
    public boolean answerToTask(String token, String answer, Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        User user = userService.getUsernameFromToken(token);
        Hacker hacker = user.getHacker();

        if (task.isEmpty())
            throw new NotFoundException("The task with id "+ task+" is not exist!", HttpStatus.BAD_GATEWAY);
        Optional<HackerTaskAnswerHistory> history = historyRepository.findHackerTaskAnswerHistoryByTaskIdAndHackerId(taskId, hacker.getId());


        if (!history.isEmpty())
            throw new NotFoundException("hacker already answered to this task at: "+historyRepository.
                    findHackerTaskAnswerHistoryByTaskIdAndHackerId(
                            taskId, hacker.getId()).
                                get().getTime()+
                    "!", HttpStatus.BAD_GATEWAY);
        if (task.get().getSubmitFlag().equals(answer)){

            if (historyRepository.findHackerTaskAnswerHistoryByTaskIdAndHackerId(taskId,hacker.getId()).isEmpty()){
                history.get().setTask(task.get());
                history.get().setHacker(hacker);
                history.get().setTime(LocalDateTime.now().toString());
                historyRepository.save(history.get());
            }
            else {
                throw new BadRequestException("hacker with id: "+hacker.getId()+" is already answered to the task with id: "+taskId+"!");
            }

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

    @Override
    public void joinToEvent(JoinEvent joinEvent, String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can join!");
        Hacker hacker = user.getHacker();
        if (eventRepository.findById(joinEvent.getEventId()).isEmpty())
            throw new BadRequestException("event not found with id: "+joinEvent.getEventId()+"!");
        Event event = eventRepository.findById(joinEvent.getEventId()).get();
        if (event.getEventType().getTitle().equals("private")){
            if (!joinEvent.getKey().equals(event.getKey()))
                throw new BadRequestException("the key for event is wrong!");
        }
        List<Hacker> hackers = new ArrayList<>();
        if (!event.getJoinedHackers().isEmpty())
            hackers = event.getJoinedHackers();
        hackers.add(hacker);
        event.setJoinedHackers(hackers);
        eventRepository.save(event);

    }

    @Override
    public void update(HackerUpdateRequest request) {

    }
}
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUBnbWFpbC5jb20iLCJhdXRoIjp7ImF1dGhvcml0eSI6IkhBQ0tFUiJ9LCJpYXQiOjE3MDM3NTYwMDAsImV4cCI6MTcwNDA1NjAwMH0.68pkfCfCDPrLti0QgTxH8z-dJhblJSVUbf_gB7EZ1qU