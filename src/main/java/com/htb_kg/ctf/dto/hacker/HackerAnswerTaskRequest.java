package com.htb_kg.ctf.dto.hacker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HackerAnswerTaskRequest {
    private String answer;
    private Long taskId;
}
