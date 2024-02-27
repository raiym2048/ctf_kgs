package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.event.EventScoreBoardResponse;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyCreateRequest;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.*;
import com.htb_kg.ctf.enums.Role;
import com.htb_kg.ctf.exception.BadCredentialsException;
import com.htb_kg.ctf.exception.BadRequestException;
import com.htb_kg.ctf.mapper.JeopardyMapper;
import com.htb_kg.ctf.repositories.LocationRepository;
import com.htb_kg.ctf.repositories.TaskRepository;
import com.htb_kg.ctf.repositories.UserRepository;
import com.htb_kg.ctf.repositories.event.*;
import com.htb_kg.ctf.service.EventService;
import com.htb_kg.ctf.service.TaskService;
import com.htb_kg.ctf.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserService userService;
    private final EventStatusRepository eventStatusRepository;
    private final EventFormatRepository eventFormatRepository;
    private final EventTypeRepository eventTypeRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;
    private final JeopardyMapper jeopardyMapper;
    private final TaskService taskService;
    private final EventScoreBoardRepository eventScoreBoardRepository;
    private final UserRepository userRepository;

    @Override
    public String create(JeopardyCreateRequest createRequest, String token) {
        User user =  userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.ADMIN))
            throw new BadCredentialsException("only admin can create the event!");
        Event event = new Event();
        event.setTitle(createRequest.getEventTitle());
        event.setEventStatus(findEvenStatus(createRequest.getEventStatus()));
        event.setEventFormat(findEventFormat(createRequest.getEventFormat()));
        event.setEventType(findEventType(createRequest.getEventType()));
        event.setLocation(findLocation(createRequest.getLocation()));
        event.setStartDate(createRequest.getStartDate());
        event.setEndDate(createRequest.getEndDate());
        event.setChallenges(findChallenges(createRequest.getChallengeIds()));
        if (createRequest.getEventType().equals("private")){
            String key = UUID.randomUUID().toString();
            event.setKey(key);
            eventRepository.save(event);
            return event.getKey();
        }
        eventRepository.save(event);
        return null;
    }

    @Override
    public List<JeopardyResponse> getAll() {
        return jeopardyMapper.toDtoS(eventRepository.findAll());
    }

    @Override
    public List<TaskResponse> eventTasks(Long eventId, String token) {
        User user = userService.getUsernameFromToken(token);


        return taskService.getAllEventTasks(eventId, user.getHacker());
    }

    @Override
    public Integer pastCount(String token) {
        User user = userService.getUsernameFromToken(token);

        return eventRepository.findByEndDateBefore(LocalDateTime.now()).size();
    }

    @Override
    public List<JeopardyResponse> pastEvents() {
        List<Event> pastEvents = eventRepository.findByEndDateBefore(LocalDateTime.now());
        pastEvents.forEach(event -> {
            event.setEventStatus(eventStatusRepository.findByTitle("past").get()); // Assuming 'setStatus' is a method in your Event entity
            eventRepository.save(event); // Save each event after setting the status
        });

        return jeopardyMapper.toDtoS(pastEvents);
    }

    @Override
    public List<JeopardyResponse> pastEvents(String token) {
        User user = userService.getUsernameFromToken(token);
        List<Event> pastEvents = eventRepository.findByEndDateBefore(LocalDateTime.now());
        pastEvents.forEach(event -> {
            event.setEventStatus(eventStatusRepository.findByTitle("past").get()); // Assuming 'setStatus' is a method in your Event entity
            eventRepository.save(event); // Save each event after setting the status
        });

        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can view events!");

        return jeopardyMapper.toDtoS(pastEvents, user.getHacker());
    }

    @Override
    public List<JeopardyResponse> ongoing() {
        List<Event> ongoingEvents = eventRepository.findAllByStartDateBeforeAndEndDateAfter(LocalDateTime.now(), LocalDateTime.now());
        ongoingEvents.forEach(event -> {
            event.setEventStatus(eventStatusRepository.findByTitle("ongoing").get()); // Assuming 'setStatus' is a method in your Event entity
            eventRepository.save(event); // Save each event after setting the status
        });        return jeopardyMapper.toDtoS(ongoingEvents);
    }

    @Override
    public List<JeopardyResponse> upcoming() {
        List<Event> upcomingEvents = eventRepository.findAllByStartDateAfter(LocalDateTime.now());
        upcomingEvents.forEach(event -> {
            event.setEventStatus(eventStatusRepository.findByTitle("upcoming").get()); // Assuming 'setStatus' is a method in your Event entity
            eventRepository.save(event); // Save each event after setting the status
        });
            return jeopardyMapper.toDtoS(upcomingEvents);
    }

    @Override
    public List<JeopardyResponse> hackerPastEvents(String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers have an pasted(finished) events!");

        return jeopardyMapper.toDtoS(user.getHacker().getHackerPastEvents());
    }

    @Override
    public void joinHacker(Long eventId, String token) {
        System.out.println("\n\n\ncalling joined func");
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new BadRequestException("no event with id: "+ eventId+"!");
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can join to event!");
        List<Hacker> joinedHackers = event.get().getJoinedHackers();
        if (joinedHackers.isEmpty())
            joinedHackers = new ArrayList<>();
        if (!joinedHackers.contains(user.getHacker())){
            System.out.println("\n\n\njoined successfully");
            joinedHackers.add(user.getHacker());
            event.get().setJoinedHackers(joinedHackers);
        }
        else {
            System.out.println("\n\n\njoined already");

        }
        eventRepository.save(event.get());

        eventScoreBoardSave(event.get(), user.getHacker());
    }
    @Override
    public void eventScoreBoardSave(Event event, Hacker hacker) {
        Optional<EventScoreBoard> eventScoreBoardOptional = eventScoreBoardRepository.findByEventAndHacker(event, hacker);
        if (eventScoreBoardOptional.isEmpty()){
            EventScoreBoard eventScoreBoard = new EventScoreBoard();
            eventScoreBoard.setEvent(event);
            eventScoreBoard.setPoint(0);
            eventScoreBoard.setHacker(hacker);
            eventScoreBoard.setSubmittedTasks(new ArrayList<>());
            eventScoreBoardRepository.save(eventScoreBoard);
        }
    }

    @Override
    public List<JeopardyResponse> hackerJoinedEvents(String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can view their joined events!");
        List<Hacker> hackers = new ArrayList<>();
        hackers.add(user.getHacker());
        List<Event> hackerJoinedEvents = eventRepository.findAllEventByJoinedHackersContaining(user.getHacker());
        if (hackerJoinedEvents.isEmpty()){
            System.out.println("\n\n\nthe list is empty!");
        }
        else {
            System.out.println("the size of the list: " + hackerJoinedEvents.size());
        }

        return jeopardyMapper.toDtoS(hackerJoinedEvents);
    }

    @Override
    public JeopardyResponse getById(String token, Long eventId) {
        User user = userService.nullableUserFromToken(token);

        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new BadRequestException("no event with id: "+eventId+"!");
        if (user==null){
            jeopardyMapper.toDto(event.get());
        }
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can view event!");

        return jeopardyMapper.toDto(event.get(), user.getHacker());
    }

    @Override
    public List<TaskResponse> searchByEventChallenges(String token, Long eventId, String search) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can search challenges of event!");
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new BadRequestException("not find event with id: "+eventId+"!");
        return taskService.searchByChallenges(event.get().getChallenges(), user.getHacker(), search);
    }

    @Override
    public List<TaskResponse> searchByCategoryInEventChallenges(String token, Long eventId, String categoryName) {
        System.out.println("the category name: "+categoryName);
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can search challenges of event!");
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new BadRequestException("not find event with id: "+eventId+"!");
        return taskService.searchByCategoryChallenges(event.get().getChallenges(), user.getHacker(), categoryName);
    }

    @Override
    public EventScoreBoardResponse rankingById(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new BadRequestException("not find event with id: "+eventId+"!");

        EventScoreBoardResponse response = new EventScoreBoardResponse();
        response.setEventName(event.get().getTitle());
        response.setRankingResponses(jeopardyMapper.toDtoSRanking(eventScoreBoardRepository.findAllByIdOrderByPointAsc(eventId)));
        return response;
    }


    private List<Task> findChallenges(List<Long> challengeIds) {
        List<Task> challenges = new ArrayList<>();
        for (Long i: challengeIds){
            Optional<Task> task = taskRepository.findById(i);
            if (task.isEmpty())
                throw new BadRequestException("the task with id:"+i+"not found!");
            task.get().setIsPrivate(true);
            taskRepository.save(task.get());
            challenges.add(task.get());
        }
        return challenges;
    }

    private Location findLocation(String title) {
        Optional<Location> location = locationRepository.findByTitle(title);
        if (location.isEmpty())
            throw new BadRequestException("the event location not found with title:"+title);
        return location.get();
    }

    private EventType findEventType(String title) {
        Optional<EventType> eventType = eventTypeRepository.findByTitle(title);
        if (eventType.isEmpty())
            throw new BadRequestException("the event type not found with title:"+title);
        return eventType.get();
    }

    private EventFormat findEventFormat(String title) {
        Optional<EventFormat> eventFormat = eventFormatRepository.findByTitle(title);
        if (eventFormat.isEmpty())
            throw new BadRequestException("the event format not found with title:"+title);
        return eventFormat.get();
    }

    private EventStatus findEvenStatus(String title) {
        Optional<EventStatus> eventStatus = eventStatusRepository.findByTitle(title);
        if (eventStatus.isEmpty())
            throw new BadRequestException("the event status not found with title:"+title);
        return eventStatus.get();
    }

    // 6, 000 0 is 1 minute
    // 6, 000 is 6 seconds
    @Scheduled(fixedRate = 600000) // this will run the method every 600 seconds
    public void endEvent(){

        System.out.println("its working every 6 second");
        LocalDateTime now = LocalDateTime.now();

        // Check for events that should be ended
        List<Event> all = eventRepository.findAll();

        // Close each event and update it in the database
        for (Event event : all) {
            if (event.getEndDate().isBefore(now)) {
                System.out.println("past");
                event.setEventStatus(eventStatusRepository.findByTitle("past").get());
            } else if (event.getStartDate().isAfter(now)) {
                System.out.println("upcoming");
                event.setEventStatus(eventStatusRepository.findByTitle("upcoming").get());
            } else if (event.getStartDate().isBefore(now) && event.getEndDate().isAfter(now)) {
                System.out.println("ongoing");
                event.setEventStatus(eventStatusRepository.findByTitle("ongoing").get());
            }
            eventRepository.save(event);
        }

    }

}
