package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.task.HintResponse;
import com.htb_kg.ctf.entities.Hint;

import java.util.List;

public interface HintMapper {
    List<HintResponse> toResponses(List<Hint> hints);

    HintResponse toResponse(Hint hint);
}
