package com.htb_kg.ctf.dto.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinEvent {
    private Long eventId;
    private String key;
}
