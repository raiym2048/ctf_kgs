package com.htb_kg.ctf.dto.event.jeopardy;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JeopardyResponse {
    private Long jeopardyId;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String eventStatus;
    private String eventType;
    private String eventFormat;
    private String location;
    private Integer challengesCount;
    private Integer teamJoined;
    private Integer playersJoined;
}
