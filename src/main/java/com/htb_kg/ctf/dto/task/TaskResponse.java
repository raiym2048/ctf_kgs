package com.htb_kg.ctf.dto.task;

import com.htb_kg.ctf.dto.file.FileResponse;
import lombok.Data;

@Data
public class TaskResponse {
    private Long id;

    private String name;
    private String description;
    private String levelName;
    private Integer userSolves;
    private String categoryName;
    private String releaseDate;
    private String taskCreator;
    private FileResponse downloadFile;
    private Integer points;
    private String submitFlag;
    private Boolean isSolved;

}