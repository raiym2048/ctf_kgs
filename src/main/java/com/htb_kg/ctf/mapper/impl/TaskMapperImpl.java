package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.task.LikeResponse;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.Task;
import com.htb_kg.ctf.mapper.FileMapper;
import com.htb_kg.ctf.mapper.HintMapper;
import com.htb_kg.ctf.mapper.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class TaskMapperImpl implements TaskMapper {
    private final FileMapper fileMapper;
    private final HintMapper hintMapper;

    @Override
    public List<TaskResponse> toDtoS(List<Task> all, List<Task> answeredTasks, Hacker hacker) {
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

            if (answeredTasks.contains(task)){

                taskResponses.add(toDto(task, true, likeResponse));
                System.out.println("istrue");
            }else {
                taskResponses.add(toDto(task, false, likeResponse));
                System.out.println("isfalse");
            }
        }
        return taskResponses;
    }

    @Override
    public TaskResponse toDto(Task task, Boolean b, LikeResponse likeResponse) {
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
        taskResponse.setIsSolved(b);
        taskResponse.setHintResponse(hintMapper.toResponses(task.getHints()));
        if (task.getHints()!=null){
            if (!task.getHints().isEmpty()){
                taskResponse.setHintText(task.getHints().get(0).getTitle());

            }
        }


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
            if (task.getLikedHackers().contains(hacker))
                taskResponses.add(toDto(task, false,likeResponse));

        }
        return taskResponses;
    }

    @Override
    public TaskResponse toDto(Task task, LikeResponse likeResponse) {
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
}