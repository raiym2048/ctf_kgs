package com.htb_kg.ctf.dto.user;

import com.htb_kg.ctf.dto.file.FileResponse;
import com.htb_kg.ctf.enums.Role;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class UserResponse {
    private Long userId;
    private FileResponse logo_image;
    private String email;
    private String nickname;
    private Role role;
    private ResponseEntity<?> teacherOrHacker;

}
