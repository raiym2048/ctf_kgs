package com.htb_kg.ctf.dto.teacher;

import com.htb_kg.ctf.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
public class TeacherResponse {
    private Long userId;
    private String email;
    private String nickname;

    private Role role;
}
