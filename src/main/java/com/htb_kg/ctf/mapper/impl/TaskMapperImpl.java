package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.hint.HintTexts;
import com.htb_kg.ctf.dto.task.HintResponse;
import com.htb_kg.ctf.dto.task.LikeResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.*;
import com.htb_kg.ctf.mapper.FileMapper;
import com.htb_kg.ctf.mapper.TaskMapper;
import com.htb_kg.ctf.repositories.OpenedHintsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TaskMapperImpl implements TaskMapper {
    private final FileMapper fileMapper;
    private final OpenedHintsRepository openedHintsRepository;


    @Override
    public List<TaskResponse> toDtoS(List<Task> all, List<Task> answeredTasks, Hacker hacker) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        Boolean onFavorite = false;


        for (Task task: all){
            if (hacker.getFavorites().contains(task))
                onFavorite = true;
            LikeResponse likeResponse = new LikeResponse();
            if (task.getLikedHackers().contains(hacker)){
                likeResponse.setLike(true);
                likeResponse.setDisLike(false);
            } else if (task.getDislikedHackers().contains(hacker)) {
                likeResponse.setLike(false);
                likeResponse.setDisLike(true);
            }
            else {
                likeResponse.setLike(false);
                likeResponse.setDisLike(false);
            }

            if (answeredTasks.contains(task)){
                TaskResponse taskResponse = toDto(task, true, likeResponse, onFavorite, hacker);
                taskResponse.setHintResponse(getHackerHints(hacker, task));
                taskResponse.setHintTexts(getHintText(hacker, task));

                taskResponses.add(taskResponse);
                System.out.println("istrue");
            }else {
                TaskResponse taskResponse = toDto(task, false, likeResponse, onFavorite, hacker);
                taskResponse.setHintResponse(getHackerHints(hacker, task));
                taskResponse.setHintTexts(getHintText(hacker, task));
                taskResponses.add(taskResponse);
                System.out.println("isfalse");
            }
        }

        return taskResponses;
    }

    @Override
    public TaskResponse toDto(Task task, Boolean b, LikeResponse likeResponse, Boolean onFavorite, Hacker hacker) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTaskCreator(task.getTaskCreator());
        taskResponse.setName(task.getName());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setPoints(task.getPoints());
        taskResponse.setReleaseDate(task.getReleaseDate());
        taskResponse.setSubmitFlag(task.getSubmitFlag());
        taskResponse.setUserSolves(task.getUserSolves());
        taskResponse.setCountLike(task.getLikedHackers().size());
        taskResponse.setLikeResponse(likeResponse);
        taskResponse.setCountDislike(task.getDislikedHackers().size());
        taskResponse.setCategoryName(task.getCategory()!=null? task.getCategory().getName():null);
        taskResponse.setLevelName(task.getLevel()!=null? task.getLevel().getName(): null);
        taskResponse.setDownloadFile(task.getDownloadFile()!=null?fileMapper.toDto(task.getDownloadFile()):null);
        if (!hacker.getAnsweredTasks().contains(task)){
            System.out.println("its false\n\n\n");
            taskResponse.setIsSolved(false);
        }else {
            System.out.println("its true\n\n\n");
            taskResponse.setIsSolved(true);
        }

//        taskResponse.setHintResponse(hintMapper.toResponses(task.getHints()));
//        if (task.getHints()!=null){
//            if (!task.getHints().isEmpty()){
//                taskResponse.setHintText(task.getHints().get(0).getTitle());
//
//            }
//        }
        taskResponse.setOnFavorite(onFavorite);


        return taskResponse;

    }






    @Override
    public List<TaskResponse> toDtoS(List<Task> all, Hacker hacker) {
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task: all){
            LikeResponse likeResponse = new LikeResponse();
            if (task.getLikedHackers().contains(hacker)){
                likeResponse.setLike(true);
                likeResponse.setDisLike(false);
            } else if (task.getDislikedHackers().contains(hacker)) {
                likeResponse.setLike(false);
                likeResponse.setDisLike(true);
            }
            else {
                likeResponse.setLike(false);
                likeResponse.setDisLike(false);
            }
                if (hacker.getFavorites().contains(task)){
                    taskResponses.add(toDto(task, false,likeResponse, true, hacker));
                }
                else {
                    taskResponses.add(toDto(task, false,likeResponse, false, hacker));

                }


        }
        return taskResponses;
    }

    @Override
    public List<TaskResponse> toDtoS(List<Task> all) {
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task: all){
             taskResponses.add(toDto(task));
        }
        return taskResponses;
    }

    @Override
    public TaskResponse toDto(Task task, LikeResponse likeResponse, Hacker hacker) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTaskCreator(task.getTaskCreator());
        taskResponse.setName(task.getName());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setPoints(task.getPoints());
        taskResponse.setReleaseDate(task.getReleaseDate());
        taskResponse.setSubmitFlag(task.getSubmitFlag());
        taskResponse.setUserSolves(task.getUserSolves());
        taskResponse.setCountLike(task.getLikedHackers().size());
        taskResponse.setCountDislike(task.getDislikedHackers().size());
        taskResponse.setLikeResponse(likeResponse);


        taskResponse.setCategoryName(task.getCategory()!=null? task.getCategory().getName():null);
        taskResponse.setLevelName(task.getLevel()!=null? task.getLevel().getName(): null);
        taskResponse.setDownloadFile(task.getDownloadFile()!=null?fileMapper.toDto(task.getDownloadFile()):null);
        taskResponse.setIsSolved(false);


        return taskResponse;

    }
    @Override
    public TaskResponse toDto(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTaskCreator(task.getTaskCreator());
        taskResponse.setName(task.getName());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setPoints(task.getPoints());
        taskResponse.setReleaseDate(task.getReleaseDate());
        taskResponse.setSubmitFlag(task.getSubmitFlag());
        taskResponse.setUserSolves(task.getUserSolves());
        taskResponse.setCountLike(task.getLikedHackers().size());
        taskResponse.setCountDislike(task.getDislikedHackers().size());

        taskResponse.setCategoryName(task.getCategory()!=null? task.getCategory().getName():null);
        taskResponse.setLevelName(task.getLevel()!=null? task.getLevel().getName(): null);
        taskResponse.setDownloadFile(task.getDownloadFile()!=null?fileMapper.toDto(task.getDownloadFile()):null);
        taskResponse.setIsSolved(false);

        return taskResponse;

    }

    @Override
    public List<HintResponse> compareAndGetNotOpenedHints(List<Hint> taskHints, List<Hint> openedHints) {
        List<Hint> notOpenedHints = new ArrayList<>();
        for (Hint hint: taskHints){
            if (!openedHints.contains(hint)){
                notOpenedHints.add(hint);
            }
        }
        return hintsToHintResponse(notOpenedHints);
    }

    private List<HintResponse> hintsToHintResponse(List<Hint> notOpenedHints) {
        List<HintResponse> hintResponses = new ArrayList<>();
        for (Hint hint: notOpenedHints){
            HintResponse hintResponse = new HintResponse();
            hintResponse.setId(hint.getId());
            hintResponse.setHint(hint.getTitle());
            hintResponses.add(hintResponse);
        }
        return hintResponses;
    }

    @Override
    public List<HintResponse> getHackerHints(Hacker hacker, Task task) {
        List<Hint> taskHints = task.getHints();
        System.out.println("the size of hints:"+taskHints.size());

        List<Hint> openedHints = openedHintsRepository.findByHackerAndHintIn(hacker, taskHints).stream().map(OpenedHints::getHint).collect(Collectors.toList());
        System.out.println("opened hints size:"+openedHints.size());
        return compareAndGetNotOpenedHints(taskHints, openedHints);

    }
    @Override
    public List<HintTexts> getHintText(Hacker hacker, Task task) {
        List<OpenedHints> openedHints = openedHintsRepository.findAllByHackerIdAndTaskId(hacker.getId(), task.getId());

        return openedHintsToHintTexts(openedHints);
    }

    @Override
    public List<TaskResponse> toDtoSEventtasks(Event event, Hacker hacker) {

        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task: event.getChallenges()){
            LikeResponse likeResponse = new LikeResponse();
            if (task.getLikedHackers().contains(hacker)){
                likeResponse.setLike(true);
                likeResponse.setDisLike(false);
            } else if (task.getDislikedHackers().contains(hacker)) {
                likeResponse.setLike(false);
                likeResponse.setDisLike(true);
            }
            else {
                likeResponse.setLike(false);
                likeResponse.setDisLike(false);
            }
            if (hacker.getFavorites().contains(task)){
                taskResponses.add(toDtoEventTask(event, task, false,likeResponse, true, hacker));
            }
            else {
                taskResponses.add(toDto(task, false,likeResponse, false, hacker));

            }


        }
        return taskResponses;
    }

    private TaskResponse toDtoEventTask(Event event,Task task, Boolean b, LikeResponse likeResponse, Boolean onFavorite, Hacker hacker) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTaskCreator(task.getTaskCreator());
        taskResponse.setName(task.getName());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setPoints(task.getPoints());
        taskResponse.setReleaseDate(task.getReleaseDate());
        taskResponse.setSubmitFlag(task.getSubmitFlag());
        taskResponse.setUserSolves(task.getUserSolves());
        taskResponse.setCountLike(task.getLikedHackers().size());
        taskResponse.setLikeResponse(likeResponse);
        taskResponse.setCountDislike(task.getDislikedHackers().size());
        taskResponse.setCategoryName(task.getCategory()!=null? task.getCategory().getName():null);
        taskResponse.setLevelName(task.getLevel()!=null? task.getLevel().getName(): null);
        taskResponse.setDownloadFile(task.getDownloadFile()!=null?fileMapper.toDto(task.getDownloadFile()):null);
        if (!hacker.getAnsweredTasks().contains(task)){
            System.out.println("its false\n\n\n");
            taskResponse.setIsSolved(false);
        }else {
            System.out.println("its true\n\n\n");
            taskResponse.setIsSolved(true);
        }
        taskResponse.setEventName(event.getTitle());

//        taskResponse.setHintResponse(hintMapper.toResponses(task.getHints()));
//        if (task.getHints()!=null){
//            if (!task.getHints().isEmpty()){
//                taskResponse.setHintText(task.getHints().get(0).getTitle());
//
//            }
//        }
        taskResponse.setOnFavorite(onFavorite);


        return taskResponse;

    }

    private List<HintTexts> openedHintsToHintTexts(List<OpenedHints> openedHints) {
        List<HintTexts> hintTexts = new ArrayList<>();
        for (OpenedHints openedHints1: openedHints){
            HintTexts text = new HintTexts();
            text.setHintText(openedHints1.getHint().getTitle());
            hintTexts.add(text);
        }
        return hintTexts;
    }


}