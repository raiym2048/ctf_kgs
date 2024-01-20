package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.RegisterHackerRequest;
import com.htb_kg.ctf.dto.auth.AuthenticationResponse;
import com.htb_kg.ctf.dto.auth.LoginRequest;
import com.htb_kg.ctf.dto.teacher.RegisterTeacherRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<AuthenticationResponse> hackerRegister(RegisterHackerRequest request);

    AuthenticationResponse login(LoginRequest request);


    void checkCode(String email, Integer code);

    void refreshPasswordSend(String emailOrNickname);

    Boolean checkRefreshCode(String emailOrNickname, Integer code);

    void newPassword(String email, String password);

    void deleteExceptoin(Long id);
}
