package com.htb_kg.ctf.dto.event.jeopardy;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JeopardyCreateRequest {
    private String eventTitle;
    private String startDate;
    private String endDate;
    private String eventStatus;
    private String eventFormat;
    private String eventType;
    private String location;
    private List<Long> challengeIds;


}
