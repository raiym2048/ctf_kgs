package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyCreateRequest;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.*;
import com.htb_kg.ctf.enums.Role;
import com.htb_kg.ctf.exception.BadCredentialsException;
import com.htb_kg.ctf.exception.BadRequestException;
import com.htb_kg.ctf.mapper.JeopardyMapper;
import com.htb_kg.ctf.repositories.LocationRepository;
import com.htb_kg.ctf.repositories.TaskRepository;
import com.htb_kg.ctf.repositories.event.EventFormatRepository;
import com.htb_kg.ctf.repositories.event.EventRepository;
import com.htb_kg.ctf.repositories.event.EventStatusRepository;
import com.htb_kg.ctf.repositories.event.EventTypeRepository;
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
import java.util.stream.Collectors;

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


        return taskService.getAllEventTasks(eventId);
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
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new BadRequestException("no event with id: "+ eventId+"!");
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can join to event!");
        List<Hacker> joinedHackers = event.get().getJoinedHackers();
        if (joinedHackers.isEmpty())
            joinedHackers = new ArrayList<>();
        joinedHackers.add(user.getHacker());
        event.get().setJoinedHackers(joinedHackers);
        eventRepository.save(event.get());
    }

    @Override
    public List<JeopardyResponse> hackerJoinedEvents(String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can view their joined events!");
        List<Hacker> hackers = new ArrayList<>();
        hackers.add(user.getHacker());
        List<Event> hackerJoinedEvents = eventRepository.findEventsByJoinedHackers(hackers);

        return jeopardyMapper.toDtoS(hackerJoinedEvents);
    }

    @Override
    public JeopardyResponse getById(String token, Long eventId) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can view event!");
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new BadRequestException("no event with id: "+eventId+"!");

        return jeopardyMapper.toDto(event.get());
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
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can search challenges of event!");
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new BadRequestException("not find event with id: "+eventId+"!");
        return taskService.searchByCategoryChallenges(event.get().getChallenges(), user.getHacker(), categoryName);
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

    @Scheduled(fixedRate = 6000) // this will run the method every 60 seconds
    public void endEvent(){
        System.out.println("its working every 6 second");
        LocalDateTime now = LocalDateTime.now();

        // Check for events that should be ended
        List<Event> eventsToClose = eventRepository.findAllByEndDateBefore(now);

        // Close each event and update it in the database
        for (Event event : eventsToClose) {
            event.setEventStatus(eventStatusRepository.findByTitle("past").get()); // assuming you have a closed flag in your Event entity
            eventRepository.save(event);
        }
    }

}
