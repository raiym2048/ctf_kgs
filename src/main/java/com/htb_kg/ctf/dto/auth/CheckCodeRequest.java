package com.htb_kg.ctf.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckCodeRequest {
    private String emailOrNickname;
    private Integer code;
}
