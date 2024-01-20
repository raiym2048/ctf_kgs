package com.htb_kg.ctf.dto.task;

import lombok.Data;

@Data
public class TaskRequest {
    private Long oldTaskId;
    private String name;
    private String description;
    private String levelName;
    private Integer userSolves;
    private String categoryName;
    private String releaseDate;
    private String taskCreator;
    private Integer points;
    private String submitFlag;

}
