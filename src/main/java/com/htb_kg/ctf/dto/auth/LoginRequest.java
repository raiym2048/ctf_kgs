package com.htb_kg.ctf.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailOrNickname;
    private String password;
}
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOnsiYXV0aG9yaXR5IjoiQURNSU4ifSwiaWF0IjoxNzA1OTE3MzQwLCJleHAiOjE3MDYyMTczNDB9.FshpeUaTadCHv426NvbuAfEQfXBZR4dMY-q9D3xqUG0