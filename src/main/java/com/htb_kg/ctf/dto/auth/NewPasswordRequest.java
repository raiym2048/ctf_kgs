package com.htb_kg.ctf.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordRequest {
    private String email;
    private String password;
}
