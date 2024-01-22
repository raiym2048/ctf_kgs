package com.htb_kg.ctf.dto.event.jeopardy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JeopardyResponse {
    private Long jeopardyId;
    private String title;
    private String startDate;
    private String endDate;
    private String eventStatus;
    private String eventType;
    private String eventFormat;
    private String location;
    private Integer challengesCount;
    private Integer teamJoined;
    private Integer playersJoined;
}
