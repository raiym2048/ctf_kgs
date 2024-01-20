package com.htb_kg.ctf.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailOrNickname;
    private String password;
}
