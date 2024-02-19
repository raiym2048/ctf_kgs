package com.htb_kg.ctf.dto.event.jeopardy;

import com.htb_kg.ctf.dto.file.FileResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class JeopardyResponse {
    private Long jeopardyId;
    private FileResponse eventImage;
    private String title;
    private Boolean joined;
    private List<Integer> startDate;
    private List<Integer> endDate;
    private String eventStatus;
    private String eventType;
    private String eventFormat;
    private String location;
    private Integer challengesCount;
    private Integer teamJoined;
    private Integer playersJoined;
}
