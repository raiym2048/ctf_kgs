package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.event.JoinEvent;
import com.htb_kg.ctf.dto.hacker.HackerProfileResponse;
import com.htb_kg.ctf.dto.hacker.HackerResponse;
import com.htb_kg.ctf.dto.hacker.HackerUpdateRequest;
import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.entities.*;
import com.htb_kg.ctf.enums.Role;
import com.htb_kg.ctf.exception.BadRequestException;
import com.htb_kg.ctf.exception.NotFoundException;
import com.htb_kg.ctf.mapper.HackerMapper;
import com.htb_kg.ctf.mapper.UserMapper;
import com.htb_kg.ctf.repositories.HackerRepository;
import com.htb_kg.ctf.repositories.TaskHackerHistoryRepository;
import com.htb_kg.ctf.repositories.TaskRepository;
import com.htb_kg.ctf.repositories.UserRepository;
import com.htb_kg.ctf.repositories.event.EventRepository;
import com.htb_kg.ctf.repositories.event.EventScoreBoardRepository;
import com.htb_kg.ctf.service.EventService;
import com.htb_kg.ctf.service.HackerService;
import com.htb_kg.ctf.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class HackerServiceImpl implements HackerService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskHackerHistoryRepository historyRepository;
    private final HackerRepository hackerRepository;
    private final UserMapper userMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final HackerMapper hackerMapper;
    private final EventScoreBoardRepository eventScoreBoardRepository;
    private final EventService eventService;
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
            if (!task.get().getIsPrivate()){
                return answerToTaskIfPublic(task,taskId, hacker);
            }
            else {
                return answerToTaskIfPrivate(task.get(),hacker);
            }

        }

        return false;
    }

    private Boolean answerToTaskIfPrivate(Task task, Hacker hacker) {
        Optional<Event> event = eventRepository.findByChallengesContains(task);
        if (event.isEmpty()){
            throw new BadRequestException("the event is private, the hacker is trying answer to the task, it mean " +
                    "this task is in event, but, why the task is not in event when we have two factor?!");
        }
        Optional<EventScoreBoard> eventScoreBoard = eventScoreBoardRepository.findByEventAndHacker(event.get(),hacker);
        if (eventScoreBoard.isEmpty()){
            createEventScoreBoard(event.get(),hacker, task);
        }
        else {
            scoreEventScoreBoard(eventScoreBoard.get(), hacker, task);
        }
        return true;
    }

    private void scoreEventScoreBoard(EventScoreBoard eventScoreBoard, Hacker hacker, Task task) {
        eventScoreBoard.setPoint(eventScoreBoard.getPoint() + task.getPoints());
        task.setPoints((int) (task.getPoints() * 0.2));
        taskRepository.save(task);
        eventScoreBoardRepository.save(eventScoreBoard);

    }

    private void createEventScoreBoard(Event event, Hacker hacker, Task task) {
        EventScoreBoard eventScoreBoard = new EventScoreBoard();
        eventScoreBoard.setHacker(hacker);
        eventScoreBoard.setEvent(event);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        eventScoreBoard.setSubmittedTasks(tasks);
        eventScoreBoard.setPoint(task.getPoints());
        task.setPoints((int) (task.getPoints() * 0.2));
        taskRepository.save(task);
        eventScoreBoardRepository.save(eventScoreBoard);
    }

    private Boolean answerToTaskIfPublic(Optional<Task> task,Long taskId,Hacker hacker ){
        task.get().setUserSolves(task.get().getUserSolves()>0?task.get().getUserSolves()+1:1);


        if (historyRepository.findHackerTaskAnswerHistoryByTaskIdAndHackerId(taskId,hacker.getId()).isEmpty()){
            HackerTaskAnswerHistory history1 = new HackerTaskAnswerHistory();
            history1.setTask(task.get());
            history1.setHacker(hacker);
            history1.setTime(LocalDateTime.now().toString());
            historyRepository.save(history1);
        }
        else {
            throw new BadRequestException("hacker with id: "+hacker.getId()+" is already answered to the task with id: "+taskId+"!");
        }

        hacker.setPoints(hacker.getPoints()==null?task.get().getPoints(): hacker.getPoints()+ task.get().getPoints());

        if (task.get().getPoints()>100){
            task.get().setPoints((int) (task.get().getPoints()*0.2));
        }
        taskRepository.save(task.get());

        List<Task> answeredTasks = new ArrayList<>();
        if (!hacker.getAnsweredTasks().isEmpty())
            answeredTasks = hacker.getAnsweredTasks();
        if (!answeredTasks.contains(task.get())){
            answeredTasks.add(task.get());
            hacker.setAnsweredTasks(answeredTasks);
            hackerRepository.save(hacker);
        }

        return true;
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
    public void update(HackerUpdateRequest request, String token) {
        User user = userService.getUsernameFromToken(token);
        if (!userRepository.findByEmailAndNickname(request.getUsername(), request.getUsername()).isEmpty()){
            throw new BadRequestException("this nickname is busy");
        }
        user.setNickname(request.getUsername());
        userRepository.save(user);


    }

    @Override
    public HackerProfileResponse getById(String token) {
        User user = userService.getUsernameFromToken(token);
        Hacker hacker = user.getHacker();

        HackerProfileResponse response = hackerMapper.toProfileDto(hacker);
        String logoLetter = user.getNickname().substring(0,1).toUpperCase();
        if (user.getNickname().length()> 2)
            logoLetter = user.getNickname().substring(0,2).toUpperCase();
        String[] colors = {"#FFD1DC","#EFA94A" , "#7FB5B5","#5D9B9B", "#77DD77", "#FF7514", "#FF9BAA"};
        response.setLogoLetter(logoLetter);
        response.setColor(colors[randomColor(colors.length)]);
        return response;
    }
    private int randomColor(int length) {
        Random random = new Random();

        // Next int will generate a number between 0 (inclusive) and 7 (exclusive)
        return random.nextInt(length);
    }
}
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUBnbWFpbC5jb20iLCJhdXRoIjp7ImF1dGhvcml0eSI6IkhBQ0tFUiJ9LCJpYXQiOjE3MDM3NTYwMDAsImV4cCI6MTcwNDA1NjAwMH0.68pkfCfCDPrLti0QgTxH8z-dJhblJSVUbf_gB7EZ1qU