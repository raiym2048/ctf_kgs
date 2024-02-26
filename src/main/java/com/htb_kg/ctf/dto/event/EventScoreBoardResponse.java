package com.htb_kg.ctf.dto.event;

import com.htb_kg.ctf.dto.rank.RankingResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventScoreBoardResponse {
    private String eventName;
    private List<RankingResponse> rankingResponses;
}
