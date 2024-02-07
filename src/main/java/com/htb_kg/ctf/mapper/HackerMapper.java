package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.hacker.HackerProfileResponse;
import com.htb_kg.ctf.entities.Hacker;

public interface HackerMapper {
    HackerProfileResponse toProfileDto(Hacker hacker);
}
