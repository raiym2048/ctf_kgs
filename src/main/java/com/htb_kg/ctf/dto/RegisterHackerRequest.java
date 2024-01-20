package com.htb_kg.ctf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterHackerRequest {
    private String email;
    private String nickname;
    private String password;
}
