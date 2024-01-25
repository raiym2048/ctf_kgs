package com.htb_kg.ctf.mapper.impl;

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
            Boolean like = false;
            if (task.getLikedHackers().contains(hacker))
                like = true;
            if (answeredTasks.contains(task)){

                taskResponses.add(toDto(task, true, like));
                System.out.println("istrue");
            }else {
                taskResponses.add(toDto(task, false, like));
                System.out.println("isfalse");
            }
        }
        return taskResponses;
    }

    @Override
    public TaskResponse toDto(Task task, Boolean b, Boolean isLiked) {
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
        taskResponse.setIsLiked(isLiked);
        taskResponse.setCategoryName(task.getCategory()!=null? task.getCategory().getName():null);
        taskResponse.setLevelName(task.getLevel()!=null? task.getLevel().getName(): null);
        taskResponse.setDownloadFile(task.getDownloadFile()!=null?fileMapper.toDto(task.getDownloadFile()):null);
        taskResponse.setIsSolved(b);
        taskResponse.setHintResponse(hintMapper.toResponses(task.getHints()));


        return taskResponse;

    }
    @Override
    public List<TaskResponse> toDtoS(List<Task> all, Hacker hacker) {
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task: all){
            Boolean like = false;
            if (task.getLikedHackers().contains(hacker))
                like = true;
                taskResponses.add(toDto(task, false, like));

        }
        return taskResponses;
    }

    @Override
    public TaskResponse toDto(Task task, Boolean isLiked) {
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
        taskResponse.setIsLiked(isLiked);


        taskResponse.setCategoryName(task.getCategory()!=null? task.getCategory().getName():null);
        taskResponse.setLevelName(task.getLevel()!=null? task.getLevel().getName(): null);
        taskResponse.setDownloadFile(task.getDownloadFile()!=null?fileMapper.toDto(task.getDownloadFile()):null);
        taskResponse.setIsSolved(false);


        return taskResponse;

    }
}