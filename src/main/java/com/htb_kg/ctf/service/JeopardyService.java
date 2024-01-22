package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.event.jeopardy.JeopardyCreateRequest;
import com.htb_kg.ctf.dto.event.jeopardy.JeopardyResponse;

import java.util.List;

public interface JeopardyService {
    String create(JeopardyCreateRequest createRequest, String token);

    List<JeopardyResponse> getAll();
}
