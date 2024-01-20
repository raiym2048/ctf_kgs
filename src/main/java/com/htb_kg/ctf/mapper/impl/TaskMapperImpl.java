package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.task.TaskRequest;
import com.htb_kg.ctf.dto.task.TaskResponse;
import com.htb_kg.ctf.entities.HackerTaskAnswerHistory;
import com.htb_kg.ctf.entities.Task;
import com.htb_kg.ctf.mapper.FileMapper;
import com.htb_kg.ctf.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public List<TaskResponse> toDtoS(List<Task> all, List<Task> answeredTasks) {
        List<TaskResponse> taskResponses = new ArrayList<>();

        for (Task task: all){
            if (answeredTasks.contains(task)){
                taskResponses.add(toDto(task, true));
                System.out.println("istrue");
            }else {
                taskResponses.add(toDto(task, false));
                System.out.println("isfalse");
            }
        }
        return taskResponses;
    }

    private TaskResponse toDto(Task task,Boolean b) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTaskCreator(task.getTaskCreator());
        taskResponse.setName(task.getName());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setPoints(task.getPoints());
        taskResponse.setReleaseDate(task.getReleaseDate());
        taskResponse.setSubmitFlag(task.getSubmitFlag());
        taskResponse.setUserSolves(task.getUserSolves());

        taskResponse.setCategoryName(task.getCategory()!=null? task.getCategory().getName():null);
        taskResponse.setLevelName(task.getLevel()!=null? task.getLevel().getName(): null);
        taskResponse.setDownloadFile(task.getDownloadFile()!=null?fileMapper.toDto(task.getDownloadFile()):null);
        taskResponse.setIsSolved(b);


        return taskResponse;

    }
}