package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.task.HintResponse;
import com.htb_kg.ctf.entities.Hint;
import com.htb_kg.ctf.mapper.HintMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HintMapperImpl implements HintMapper {
    @Override
    public List<HintResponse> toResponses(List<Hint> hints) {
        List<HintResponse> hintResponses = new ArrayList<>();
        for (Hint hint: hints)
            hintResponses.add(toResponse(hint));
        return hintResponses;
    }

    @Override
    public HintResponse toResponse(Hint hint) {
        HintResponse hintResponse = new HintResponse();
        hintResponse.setId(hint.getId());
        hintResponse.setHint(hint.getTitle());
       // hintResponse.setUsable(hint.getUsable());
        return hintResponse;
    }
}
