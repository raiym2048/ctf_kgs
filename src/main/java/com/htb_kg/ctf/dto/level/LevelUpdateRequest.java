package com.htb_kg.ctf.dto.level;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelUpdateRequest {
    private String oldLevel;
    private String newLevel;
}
