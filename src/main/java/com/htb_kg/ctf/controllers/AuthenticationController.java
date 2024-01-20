package com.htb_kg.ctf.controllers;


import com.htb_kg.ctf.dto.RegisterHackerRequest;
import com.htb_kg.ctf.dto.auth.CheckCodeRequest;
import com.htb_kg.ctf.dto.auth.CheckRequest;
import com.htb_kg.ctf.dto.auth.LoginRequest;
import com.htb_kg.ctf.dto.auth.NewPasswordRequest;
import com.htb_kg.ctf.dto.teacher.RegisterTeacherRequest;
import com.htb_kg.ctf.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register/hacker")
    public ResponseEntity<?> hackerRegister(@RequestBody RegisterHackerRequest request) {
        return service.hackerRegister(request);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/register/code")
    public void checkCode(@RequestParam(required = false) String email, @RequestParam(required = false) Integer code){
        service.checkCode(email, code);
    }

    @PostMapping("/refresh_send")
    public void sendCodeRefreshPassword(@RequestBody CheckRequest checkRequest){
        System.out.println("the jj"+checkRequest.getEmailNickname());
        service.refreshPasswordSend(checkRequest.getEmailNickname());
    }
    @PostMapping("/refresh_check")
    public Boolean checkRefreshCode(@RequestBody CheckCodeRequest code){
        return service.checkRefreshCode(code.getEmailOrNickname(),code.getCode());
    }
    @PostMapping("/newPassword")
    public void newPassword(@RequestBody NewPasswordRequest newPasswordRequest){
        service.newPassword(newPasswordRequest.getEmail(), newPasswordRequest.getPassword());
    }


    @DeleteMapping("/delete/{id}")
    public void uhh(@PathVariable Long id){
        service.deleteExceptoin(id);
    }
}
