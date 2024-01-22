package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;
import com.htb_kg.ctf.entities.Event;
import com.htb_kg.ctf.entities.Jeopardy;

import java.util.List;

public interface JeopardyMapper {
    List<JeopardyResponse> toDtoS(List<Event> all);

    JeopardyResponse toDto(Event jeopardy);
}
