//package com.htb_kg.ctf.dataLoader;
//
//import com.htb_kg.ctf.entities.*;
//import com.htb_kg.ctf.enums.Role;
//import com.htb_kg.ctf.repositories.*;
//import com.htb_kg.ctf.repositories.event.EventFormatRepository;
//import com.htb_kg.ctf.repositories.event.EventRepository;
//import com.htb_kg.ctf.repositories.event.EventStatusRepository;
//import com.htb_kg.ctf.repositories.event.EventTypeRepository;
//import com.htb_kg.ctf.service.CategoryService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.Random;
//
//@Component
//@AllArgsConstructor
//public class DataLoader implements CommandLineRunner {
//    private final UserRepository userRepository;
//    private final CategoryRepository categoryRepository;
//    private final LevelRepository levelRepository;
//    private final TaskRepository taskRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final EventTypeRepository eventTypeRepository;
//    private final EventStatusRepository eventStatusRepository;
//    private final EventFormatRepository eventFormatRepository;
//    private final LocationRepository locationRepository;
//    private final TeamRepository teamRepository;
//    private final EventRepository eventRepository;
//    @Override
//    public void run(String... args) throws Exception {
//        if (eventTypeRepository.findAll().size()< 10){
//            for (int i = 1;i < 11;i ++){
//                EventType eventType = new EventType();
//                eventType.setTitle("eventType"+i);
//                eventTypeRepository.save(eventType);
//            }
//        }
//        if (eventFormatRepository.findAll().size()< 10){
//            for (int i = 1;i < 11;i ++){
//                EventFormat eventFormat = new EventFormat();
//                eventFormat.setTitle("eventFormat"+i);
//                eventFormatRepository.save(eventFormat);
//            }
//        }
//        if (eventStatusRepository.findAll().size()< 10){
//            for (int i = 1;i < 11;i ++){
//                EventStatus eventStatus = new EventStatus();
//                eventStatus.setTitle("eventStatus"+i);
//                eventStatusRepository.save(eventStatus);
//            }
//        }
//        if (locationRepository.findAll().size()< 10){
//            for (int i = 1;i < 11;i ++){
//                Location location = new Location();
//                location.setTitle("location"+i);
//                locationRepository.save(location);
//            }
//        }
//
//        if (userRepository.findByRole(Role.ADMIN).isEmpty()){
//            User user = new User();
//            user.setRole(Role.ADMIN);
//            user.setEmail("admin");
//            user.setCheckCode(100);
//            user.setPassword("$2a$12$xtE1.bglHHAxHtsx7GIGIOthmbXbZ/DC9deNn2.cL5izT4o04Oa8W");
//            userRepository.save(user);
//        }
//        if (categoryRepository.findAll().size()< 5)
//            for (int i = 1;i < 11;i++){
//                Category category = new Category();
//                category.setName("category "+i+"!");
//                categoryRepository.save(category);
//            }
//        if (levelRepository.findAll().size()<5){
//            for (int i = 1;i < 11;i++){
//                Level level = new Level();
//                level.setName("level "+i);
//                levelRepository.save(level);
//            }
//        }
//        if (taskRepository.findAll().size()<5){
//            Random random = new Random();
//            for (int i = 1;i < 11;i++){
//
//                Task task = new Task();
//                task.setTaskCreator("raiymbek");
//                task.setPoints(500);
//                task.setName("simple task name");
//                task.setUserSolves(0);
//                task.setDescription("description");
//                task.setReleaseDate(LocalDateTime.now().toString());
//                task.setLevel(levelRepository.findById(1L).get());
//                task.setCategory(categoryRepository.findById(1L).get());
//                task.setSubmitFlag("simple flag");
//                taskRepository.save(task);
//            }
//        }
//        if (userRepository.findByRole(Role.HACKER).isEmpty()){
//            User user = new User();
//            user.setEmail("user");
//            user.setRole(Role.HACKER);
//            user.setPassword("$2a$12$xtE1.bglHHAxHtsx7GIGIOthmbXbZ/DC9deNn2.cL5izT4o04Oa8W\n");
//            Hacker hacker = new Hacker();
//            user.setHacker(hacker);
//            userRepository.save(user);
//        }
//        if (eventTypeRepository.findAll().isEmpty()){
//
//        }
//    }
//}
