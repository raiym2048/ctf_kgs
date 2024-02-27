package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.hint.HintTexts;
import com.htb_kg.ctf.dto.task.*;
import com.htb_kg.ctf.entities.*;
import com.htb_kg.ctf.enums.Role;
import com.htb_kg.ctf.exception.BadCredentialsException;
import com.htb_kg.ctf.exception.BadRequestException;
import com.htb_kg.ctf.exception.NotFoundException;
import com.htb_kg.ctf.mapper.TaskMapper;
import com.htb_kg.ctf.repositories.*;
import com.htb_kg.ctf.repositories.event.EventRepository;
import com.htb_kg.ctf.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final LevelService levelService;
    private final LevelRepository levelRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final HackerRepository hackerRepository;
    private final HintRepository hintRepository;
    private final OpenedHintsRepository openedHintsRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final FileDataService fileService;

    @Override
    public void addTask(TaskRequest taskRequest, String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.ADMIN))
            throw new BadRequestException("only admins can add task!");
        Task task = requestToEntity(taskRequest);
        task.setHints(setHintTask(taskRequest.getHintRequests()));


        taskRepository.save(task);

    }

    private List<Hint> setHintTask(List<HintRequest> hintRequests) {
        List<Hint> hints = new ArrayList<>();
        for (HintRequest request: hintRequests){
            Hint hint = new Hint();
            hint.setTitle(request.getTitle());
            hintRepository.save(hint);
            hints.add(hint);

        }
        return hints;
    }


    @Override
    @Transactional
    public void updateTask(TaskRequest taskRequest, Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty())
            throw new NotFoundException("task with id "+taskId+" is not found!", HttpStatus.BAD_GATEWAY);
        taskRepository.updateTaskAttributes(taskId,
                taskRequest.getName(), taskRequest.getDescription(), taskRequest.getPoints(),taskRequest.getLevelName().isEmpty()?null: levelRepository.findByName(taskRequest.getLevelName()).get(),
                taskRequest.getUserSolves(),taskRequest.getCategoryName().isEmpty()?null: categoryRepository.findCategoryByName(taskRequest.getCategoryName()).get(),
                LocalDateTime.now(), taskRequest.getTaskCreator(),
                taskRequest.getSubmitFlag());
    }

    @Override
    public List<TaskResponse> getAll(String token) {
        User user = userService.getUsernameFromToken(token);
        List<Task> answeredTasks = user.getHacker().getAnsweredTasks();
//        if (!user.getRole().equals(Role.HACKER))
//            throw new BadRequestException("only hacker can favorite!");
        Hacker hacker = user.getHacker();

        System.out.println("the size:"+answeredTasks.size());
        List<TaskResponse> taskResponses = taskMapper.toDtoS(taskRepository.findAllByIsPrivate(false), answeredTasks, hacker);
        System.out.println("the size of tasks for all:"+ taskResponses.size());

        return taskResponses;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty())
            throw new NotFoundException("Task with id "+id+" is not exist!", HttpStatus.BAD_GATEWAY);
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskResponse> filter(Boolean s1, Boolean s2, Boolean s3, String token) {
        List<Task> tasks = taskRepository.findAllByIsPrivate(false);
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hacker can!");
        Hacker hacker = user.getHacker();
        List<Task> answeredTasks = user.getHacker().getAnsweredTasks();
        if (s1){
            tasks.removeAll(answeredTasks);
            return taskMapper.toDtoS(tasks,hacker );
        }

        return taskMapper.toDtoS(tasks, answeredTasks, hacker);
    }

    @Override
    public Boolean favorite(Long taskId, String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hacker can favorite!");
        Hacker hacker = user.getHacker();
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty())
            throw new NotFoundException("Task with id "+task+" is not exist!", HttpStatus.BAD_GATEWAY);

        List<Task> favorites = new ArrayList<>();
        if (!hacker.getFavorites().isEmpty()){
            favorites = hacker.getFavorites();
            if (!hacker.getFavorites().contains(task.get())){
                favorites.add(task.get());
                hacker.setFavorites(favorites);
                hackerRepository.save(hacker);
                return true;
            }else {
                favorites.remove(task.get());
                hacker.setFavorites(favorites);
                hackerRepository.save(hacker);
                return false;
            }

        }
        else {
            favorites.add(task.get());
            hacker.setFavorites(favorites);
            hackerRepository.save(hacker);
            return true;
        }
    }

    @Override
    public TaskResponse getById(Long taskId, String token) {
        TaskResponse taskResponse = new TaskResponse();
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hacker can favorite!");
        Hacker hacker = user.getHacker();
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty())
            throw new NotFoundException("Task with id "+task+" is not exist!", HttpStatus.BAD_GATEWAY);
        List<Task> answeredTasks = hacker.getAnsweredTasks();
        LikeResponse likeResponse = new LikeResponse();
        if (task.get().getLikedHackers().contains(hacker)){
            likeResponse.setLike(true);
            likeResponse.setDisLike(false);
        } else if (task.get().getDislikedHackers().contains(hacker)) {
            likeResponse.setLike(false);
            likeResponse.setDisLike(true);
        }
        else {
            likeResponse.setLike(false);
            likeResponse.setDisLike(false);
        }
        if (hacker.getFavorites().contains(task.get())){
            taskResponse = taskMapper.toDto(task.get(), answeredTasks.contains(task.get()),likeResponse, true , hacker);
            taskResponse.setHintResponse(taskMapper.getHackerHints(hacker, task.get()));
            taskResponse.setHintTexts(taskMapper.getHintText(hacker, task.get()));
            return taskResponse;


        }

        taskResponse = taskMapper.toDto(task.get(), answeredTasks.contains(task.get()),likeResponse, false , hacker);
        taskResponse.setHintResponse(taskMapper.getHackerHints(hacker, task.get()));
        taskResponse.setHintTexts(taskMapper.getHintText(hacker, task.get()));


        return taskResponse;
    }










    @Override
    public LikeResponse likeTask(Long taskId, String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hacker can favorite!");
        Hacker hacker = user.getHacker();
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty())
            throw new NotFoundException("Task with id "+task+" is not exist!", HttpStatus.BAD_GATEWAY);


        if (!task.get().getLikedHackers().contains(hacker)){
            List<Hacker> likedHackers = new ArrayList<>();
            if (!task.get().getLikedHackers().isEmpty()){
                likedHackers = task.get().getLikedHackers();
            }
            likedHackers.add(hacker);
            task.get().setLikedHackers(likedHackers);
            if (task.get().getDislikedHackers().contains(hacker)){
                task.get().getDislikedHackers().remove(hacker);
            }

            taskRepository.save(task.get());
            LikeResponse likeResponse = new LikeResponse();
            likeResponse.setLike(true);
            likeResponse.setDisLike(false);
            return likeResponse;
        }
        else {
            LikeResponse likeResponse = new LikeResponse();
            likeResponse.setLike(false);
            likeResponse.setDisLike(false);
        }


        return null;

    }
    @Override
    public LikeResponse disLikeTask(Long taskId, String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hacker can favorite!");
        Hacker hacker = user.getHacker();
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty())
            throw new NotFoundException("Task with id "+task+" is not exist!", HttpStatus.BAD_GATEWAY);


        if (!task.get().getDislikedHackers().contains(hacker)){
            List<Hacker> disLikedHackers = new ArrayList<>();
            if (!task.get().getDislikedHackers().isEmpty()){
                disLikedHackers = task.get().getLikedHackers();
            }
            disLikedHackers.add(hacker);
            task.get().setLikedHackers(disLikedHackers);

            if (task.get().getLikedHackers().contains(hacker)){
                task.get().getLikedHackers().remove(hacker);
            }

            taskRepository.save(task.get());
            LikeResponse likeResponse = new LikeResponse();
            likeResponse.setLike(false);
            likeResponse.setDisLike(true);


            return likeResponse;
        }
        else {
            LikeResponse likeResponse = new LikeResponse();
            likeResponse.setLike(false);
            likeResponse.setDisLike(false);
        }


        return null;

    }

    @Override
    public String openHint(Long id, String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hacker can favorite!");
        Hacker hacker = user.getHacker();
        Optional<Hint> hint =  hintRepository.findById(id);
        if (hint.isEmpty())
            throw new BadRequestException("hint not found with id: "+id);
        if (openedHintsRepository.findByHintIdAndHackerId(hint.get().getId(), hacker.getId()).isEmpty()){
            OpenedHints openedHints = new OpenedHints();
            openedHints.setHint(hint.get());
            openedHints.setHacker(hacker);
            openedHints.setTask(taskRepository.findByHintsIdAndIsPrivate(id, false).orElseThrow());
            openedHintsRepository.save(openedHints);
            if (user.getHacker().getPoints()< 10){
                throw new BadRequestException("hacker's points is over than 10. He can not open the hint!");
            }
            user.getHacker().setPoints(user.getHacker().getPoints()-10);
            userRepository.save(user);

        }
       // hint.get().setUsable(false);
        hintRepository.save(hint.get());


        return hint.get().getTitle();
    }

    @Override
    public List<TaskResponse> byCategory(String request, String token) {
        List<Task> tasks = taskRepository.findAllByCategoryNameAndIsPrivate(request, false);
        if (!request.matches(".*\\w.*")){
            System.out.println("its an empty!");
            tasks= taskRepository.findAllByIsPrivate(false);
        }
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hacker can!");
        Hacker hacker = user.getHacker();
        List<Task> answeredTasks = user.getHacker().getAnsweredTasks();

        return taskMapper.toDtoS(tasks, answeredTasks, hacker);
    }

    @Override
    public List<TaskResponse> search(String searchRequest, String token) {
        List<Task> tasks = new ArrayList<>();
        if (!searchRequest.matches(".*\\w.*")){
            System.out.println("its an empty!");
            tasks= taskRepository.findAllByIsPrivate(false);
        }
        else {
            tasks = taskRepository.findAllByNameContainingAndIsPrivate(searchRequest, false);

        }
        User user = userService.getUsernameFromToken(token);
        Hacker hacker = user.getHacker();
        List<Task> answeredTasks = user.getHacker().getAnsweredTasks();
        if (user.getRole().equals(Role.HACKER)){
            return taskMapper.toDtoS(tasks, answeredTasks, hacker);
        }
        return null;
    }

    @Override
    public List<TaskResponse> getAllEventTasks(Long eventId, Hacker hacker) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new NotFoundException("no event with id: "+eventId+"!", HttpStatus.BAD_REQUEST);

        List<TaskResponse> taskResponses =  taskMapper.toDtoSEventtasks(event.get(), hacker);
        System.out.println("\n\n\nthe size of tasks in the event: "+event.get().getChallenges().size());
        System.out.println("\n\n\n the size of taskresponse for event all tasks: " + taskResponses.size());
        return taskResponses;
    }

    @Override
    public List<TaskResponse> privateTasks(String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.ADMIN))
            throw new BadRequestException("only admin can view private tasks!");

        return taskMapper.toDtoS(taskRepository.findAllByIsPrivate(true));
    }

    @Override
    public void setTaskPublic(ListId listId, String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.ADMIN))
            throw new BadCredentialsException("only admin can change type of task!");
        for (Long id: listId.getIds()){
            Optional<Task> task = taskRepository.findById(id);
            if (task.isEmpty())
                throw new BadRequestException("no task with id: "+id+"!");
            if(!task.get().getIsPrivate())
                throw new BadRequestException("task with id: "+id+" is already public!");
            task.get().setIsPrivate(false);
            taskRepository.save(task.get());
        }
    }

    @Override
    public void setTaskPrivate(ListId listId, String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.ADMIN))
            throw new BadCredentialsException("only admin can change type of task!");
        for (Long id: listId.getIds()){
            Optional<Task> task = taskRepository.findById(id);
            if (task.isEmpty())
                throw new BadRequestException("no task with id: "+id+"!");
            if(task.get().getIsPrivate())
                throw new BadRequestException("task with id: "+id+" is already private!");
            task.get().setIsPrivate(true);
            taskRepository.save(task.get());
        }
    }

    @Override
    public Integer hackerSubmittedTaskCount(String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can call method: count done tasks!");

        return user.getHacker().getAnsweredTasks().size();
    }

    @Override
    public List<TaskResponse> hackerSubmittedTasks(String token) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.HACKER))
            throw new BadRequestException("only hackers can call method: done tasks!");
        return taskMapper.toDtoS(user.getHacker().getAnsweredTasks(), user.getHacker());
    }

    @Override
    public void createTaskWithFile(TaskRequest taskRequest, String token, MultipartFile file) {
        User user = userService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.ADMIN))
            throw new BadRequestException("only admins can add task!");
        Task task = requestToEntity(taskRequest);
        task.setHints(setHintTask(taskRequest.getHintRequests()));
        task.setDownloadFile(fileService.uploadFile(file, token));


        taskRepository.save(task);
    }

    @Override
    public List<TaskResponse> searchByChallenges(List<Task> challenges, Hacker hacker, String search) {
        List<Task> searchedTasks = new ArrayList<>();

        if (!search.matches(".*\\w.*")){
            System.out.println("its an empty!");
            searchedTasks= challenges;
        }
        else {
            for (Task challenge : challenges) {
                if (challenge.getName().toLowerCase().contains(search.toLowerCase()) ||
                        challenge.getDescription().toLowerCase().contains(search.toLowerCase())) {
                    searchedTasks.add(challenge);
                }
            }
        }

        return taskMapper.toDtoS(searchedTasks, hacker);
    }

    @Override
    public List<TaskResponse> searchByCategoryChallenges(List<Task> challenges, Hacker hacker, String categoryName) {
        List<Task> searchedTasks = new ArrayList<>();

        if (!categoryName.matches(".*\\w.*")) {
            System.out.println("\n\n\nits an empty!");
            searchedTasks = challenges;
        } else {
            Optional<Category> category = categoryRepository.findCategoryByName(categoryName);
            if (category.isEmpty())
                throw new BadRequestException("no category with this name! name: " + categoryName + "!");

            for (Task challenge : challenges) {
                if (challenge.getCategory().getName().equals(categoryName)){
                    searchedTasks.add(challenge);
                    System.out.println("\n\n\nits contain");
                }
                else {
                    System.out.println("\n\n\nits not contain");

                }
            }
        }
        List<TaskResponse> result = taskMapper.toDtoS(searchedTasks, hacker);
        System.out.println("\n\n\nthe size of list: "+result.size());
        return result;
    }

    private Task requestToEntity(TaskRequest taskRequest) {
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setTaskCreator(taskRequest.getTaskCreator());
        task.setPoints(taskRequest.getPoints());
        task.setDescription(taskRequest.getDescription());
        task.setReleaseDate(LocalDateTime.now());
        task.setSubmitFlag(taskRequest.getSubmitFlag());
        task.setUserSolves(taskRequest.getUserSolves());
        task.setLevel(!taskRequest.getLevelName().isBlank()? levelService.findByName(taskRequest.getLevelName()):null);
        task.setCategory(!taskRequest.getCategoryName().isEmpty()? categoryService.findByName(taskRequest.getCategoryName()):null);

       // task.setHints(toSaveHints(taskRequest.getHintRequests()));

        return task;
    }

    private List<Hint> toSaveHints(List<HintRequest> hintRequests) {
        List<Hint> hints = new ArrayList<>();
        for (HintRequest hintRequest: hintRequests){
            Hint hint = new Hint();
            hint.setTitle(hintRequest.getTitle());
            hintRepository.save(hint);
            hints.add(hint);
        }
        return hints;
    }
}
