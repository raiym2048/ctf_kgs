package com.htb_kg.ctf.dto.task;

import com.htb_kg.ctf.dto.file.FileResponse;
import com.htb_kg.ctf.dto.hint.HintTexts;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskResponse {
    private Long id;

    private String name;
    private String description;
    private String levelName;
    private Integer userSolves;
    private String categoryName;
    private LocalDateTime releaseDate;
    private String taskCreator;
    private FileResponse downloadFile;
    private Integer points;
    private String submitFlag;
    private Boolean isSolved;
    private LikeResponse likeResponse;
    private Integer countLike;
    private Integer countDislike;
    private List<HintTexts> hintTexts;
    private List<HintResponse> hintResponse;
    private Boolean onFavorite;

}
