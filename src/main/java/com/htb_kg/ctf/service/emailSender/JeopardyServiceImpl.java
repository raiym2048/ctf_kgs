package com.htb_kg.ctf.service.emailSender;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyCreateRequest;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.entities.*;
import com.htb_kg.ctf.enums.Role;
import com.htb_kg.ctf.exception.BadCredentialsException;
import com.htb_kg.ctf.exception.BadRequestException;
import com.htb_kg.ctf.mapper.JeopardyMapper;
import com.htb_kg.ctf.repositories.JeopardyRepository;
import com.htb_kg.ctf.repositories.LocationRepository;
import com.htb_kg.ctf.repositories.TaskRepository;
import com.htb_kg.ctf.repositories.event.EventFormatRepository;
import com.htb_kg.ctf.repositories.event.EventRepository;
import com.htb_kg.ctf.repositories.event.EventStatusRepository;
import com.htb_kg.ctf.repositories.event.EventTypeRepository;
import com.htb_kg.ctf.service.JeopardyService;
import com.htb_kg.ctf.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JeopardyServiceImpl implements JeopardyService {
    private final UserService userService;
    private final EventStatusRepository eventStatusRepository;
    private final EventFormatRepository eventFormatRepository;
    private final EventTypeRepository eventTypeRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;
    private final JeopardyMapper jeopardyMapper;

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
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

            SecureRandom random = new SecureRandom();
            StringBuilder password = new StringBuilder();

            for (int i = 0; i < 12; i++) {
                int randomIndex = random.nextInt(characters.length());
                password.append(characters.charAt(randomIndex));
            }
            event.setKey(password.toString());
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

    private List<Task> findChallenges(List<Long> challengeIds) {
        List<Task> challenges = new ArrayList<>();
        for (Long i: challengeIds){
            Optional<Task> task = taskRepository.findById(i);
            if (task.isEmpty())
                throw new BadRequestException("the task with id:"+i+"not found!");
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

}