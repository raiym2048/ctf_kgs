package com.htb_kg.ctf.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTeacherRequest {
    private String email;
    private String nickname;
    private String password;
    private String file;

}
